package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.financialhelper.model.TransactionCategory;
import com.boldyrev.financialhelper.repository.TransactionCategoriesRepository;
import com.boldyrev.financialhelper.service.TransactionCategoriesService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
@Service
@RequiredArgsConstructor
public class TransactionCategoriesServiceImpl implements TransactionCategoriesService {

    private static final String DEFAULT_CATEGORY_NAME = "Прочее";

    private final TransactionCategoriesRepository categoriesRepository;

    @Override
    @Cacheable("categories")
    public Flux<TransactionCategory> getCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    @Cacheable("categoriesMap")
    public Mono<Map<String, TransactionCategory>> getCategoriesMap() {
        return categoriesRepository.findAll()
            .collectMap(TransactionCategory::getCategoryName);
    }

    @Override
    @Cacheable("defaultCategory")
    public Mono<TransactionCategory> getDefaultCategory() {
        return categoriesRepository.findAllByCategoryName(DEFAULT_CATEGORY_NAME);
    }
}
