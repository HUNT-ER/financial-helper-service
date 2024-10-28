package com.boldyrev.financialhelper.repository;

import com.boldyrev.financialhelper.model.TransactionCategory;
import java.util.UUID;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
public interface TransactionCategoriesRepository extends
    ReactiveCrudRepository<TransactionCategory, UUID> {

    Mono<TransactionCategory> findAllByCategoryName(String categoryName);
}