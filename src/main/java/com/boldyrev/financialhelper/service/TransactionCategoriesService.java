package com.boldyrev.financialhelper.service;

import com.boldyrev.financialhelper.model.TransactionCategory;
import java.util.Map;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
public interface TransactionCategoriesService {

    Flux<TransactionCategory> getCategories();

    Mono<Map<String, TransactionCategory>> getCategoriesMap();

    Mono<TransactionCategory> getDefaultCategory();
}
