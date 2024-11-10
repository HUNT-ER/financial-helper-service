package com.boldyrev.financialhelper.service;


import com.boldyrev.dto.TransactionCreationDto;
import com.boldyrev.dto.TransactionsFilter;
import com.boldyrev.dto.TransactionsResponse;
import com.boldyrev.financialhelper.dto.TransactionDto;
import java.util.List;
import java.util.UUID;
import reactor.core.publisher.Mono;

/**
 * Service for operations with {@link com.boldyrev.financialhelper.model.Transaction}.
 *
 * @author Alexandr Boldyrev
 */
public interface TransactionsService {

    /**
     * Saving list of {@link com.boldyrev.financialhelper.model.Transaction}.
     *
     * @param transactions list of {@link com.boldyrev.financialhelper.model.Transaction}
     * @return void
     */
    Mono<Void> saveTransactions(List<TransactionDto> transactions);

    /**
     * Saving new transaction.
     *
     * @param transactionDto new transaction.
     * @return void
     */
    Mono<Void> addTransaction(TransactionCreationDto transactionDto);

    /**
     * Get all user transactions by filter.
     *
     * @param userId user ID
     * @param filter filter data
     * @return found transactions by filter
     */
    Mono<TransactionsResponse> getTransactionsByFilter(Long userId, TransactionsFilter filter);

    /**
     * Delete transaction by id.
     * @param transactionId transaction id
     * @return void
     */
    Mono<Void> deleteTransaction(UUID transactionId);
}
