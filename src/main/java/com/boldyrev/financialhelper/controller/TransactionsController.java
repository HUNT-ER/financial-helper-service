package com.boldyrev.financialhelper.controller;

import com.boldyrev.controller.TransactionsApi;
import com.boldyrev.dto.TransactionCreationDto;
import com.boldyrev.dto.TransactionsFilter;
import com.boldyrev.dto.TransactionsResponse;
import com.boldyrev.financialhelper.service.TransactionsService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Rest controller for operations with transaction.
 *
 * @author Alexandr Boldyrev
 */
@RestController
@RequiredArgsConstructor
public class TransactionsController implements TransactionsApi {

    private final TransactionsService transactionsService;

    @Override
    public Mono<ResponseEntity<Void>> addTransaction(Mono<TransactionCreationDto> transactionDto,
        ServerWebExchange exchange) {
        return transactionDto.flatMap(transactionsService::addTransaction)
            .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }

    @Override
    public Mono<ResponseEntity<TransactionsResponse>> getUserTransactions(
        Long userId, Mono<TransactionsFilter> transactionsFilter, ServerWebExchange exchange) {
        return transactionsFilter.flatMap(
                filter -> transactionsService.getTransactionsByFilter(userId, filter))
            .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteTransaction(UUID transactionId,
        ServerWebExchange exchange) {
        return transactionsService.deleteTransaction(transactionId)
            .thenReturn(ResponseEntity.noContent().build());
    }
}
