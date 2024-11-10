package com.boldyrev.financialhelper.controller;

import com.boldyrev.controller.TransactionCategoriesApi;
import com.boldyrev.dto.TransactionCategoriesResponse;
import com.boldyrev.financialhelper.mapper.TransactionCategoryMapper;
import com.boldyrev.financialhelper.service.TransactionCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Rest controller for operations with transaction categories.
 *
 * @author Alexandr Boldyrev
 */
@RestController
@RequiredArgsConstructor
public class TransactionCategoriesController implements TransactionCategoriesApi {

    private final TransactionCategoriesService categoriesService;

    private final TransactionCategoryMapper mapper;

    @Override
    public Mono<ResponseEntity<TransactionCategoriesResponse>> getAllTransactionCategories(
        ServerWebExchange exchange) {
        return categoriesService.getCategories()
            .collectList()
            .map(mapper::toTransactionCategoriesResponse)
            .map(ResponseEntity::ok);
    }
}
