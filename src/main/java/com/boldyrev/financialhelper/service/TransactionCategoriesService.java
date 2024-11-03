package com.boldyrev.financialhelper.service;

import com.boldyrev.financialhelper.dto.TransactionCategoryDto;
import java.util.Map;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service for process operations with
 * {@link com.boldyrev.financialhelper.model.TransactionCategory}.
 *
 * @author Alexandr Boldyrev
 */
public interface TransactionCategoriesService {

    /**
     * Getting all categories {@link com.boldyrev.financialhelper.model.TransactionCategory} as DTO
     * {@link TransactionCategoryDto}.
     *
     * @return list of {@link TransactionCategoryDto}
     */
    Flux<TransactionCategoryDto> getCategories();

    /**
     * Getting all categories in {@link Map}.
     *
     * @return map with key equal {@link TransactionCategoryDto#getCategoryName()} and value
     * {@link TransactionCategoryDto}.
     */
    Mono<Map<String, TransactionCategoryDto>> getCategoriesMap();

    /**
     * Get default category.
     * Default category applies to all not recognized items.
     *
     * @return default category.
     */
    Mono<TransactionCategoryDto> getDefaultCategory();
}
