package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.financialhelper.dto.TransactionCategoryDto;
import com.boldyrev.financialhelper.mapper.TransactionCategoryMapper;
import com.boldyrev.financialhelper.repository.TransactionCategoriesRepository;
import com.boldyrev.financialhelper.service.TransactionCategoriesService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link TransactionCategoriesService}.
 *
 * @author Alexandr Boldyrev
 */
@Service
@RequiredArgsConstructor
public class TransactionCategoriesServiceImpl implements TransactionCategoriesService {

    @Value("${app.default-category}")
    private String defaultCategoryName;

    private final TransactionCategoriesRepository categoriesRepository;

    private final TransactionCategoryMapper mapper;

    @Override
    @Cacheable("categories")
    public Flux<TransactionCategoryDto> getCategories() {
        return categoriesRepository.findAll()
            .map(mapper::toDto);
    }

    @Override
    @Cacheable("categoriesMap")
    public Mono<Map<String, TransactionCategoryDto>> getCategoriesMap() {
        return categoriesRepository.findAll()
            .map(mapper::toDto)
            .collectMap(TransactionCategoryDto::getCategoryName);
    }

    @Override
    @Cacheable("defaultCategory")
    public Mono<TransactionCategoryDto> getDefaultCategory() {
        return categoriesRepository.findAllByCategoryName(defaultCategoryName)
            .map(mapper::toDto);
    }
}
