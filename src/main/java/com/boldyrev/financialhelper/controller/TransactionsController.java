package com.boldyrev.financialhelper.controller;

import com.boldyrev.controller.TransactionsApi;
import com.boldyrev.dto.TransactionCreationDto;
import com.boldyrev.financialhelper.service.TransactionsService;
import jakarta.validation.Valid;
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
    public Mono<ResponseEntity<Void>> addTransaction(@Valid Mono<TransactionCreationDto> transactionDto,
        ServerWebExchange exchange) {
       return transactionDto.flatMap(transactionsService::addTransaction)
            .thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    }
}
