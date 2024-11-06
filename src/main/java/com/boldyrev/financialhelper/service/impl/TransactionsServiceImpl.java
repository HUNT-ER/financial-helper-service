package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.dto.TransactionCreationDto;
import com.boldyrev.financialhelper.dto.TransactionDto;
import com.boldyrev.financialhelper.exception.TransactionCategoryNotExistsException;
import com.boldyrev.financialhelper.mapper.TransactionMapper;
import com.boldyrev.financialhelper.repository.TransactionsRepository;
import com.boldyrev.financialhelper.service.TransactionCategoriesService;
import com.boldyrev.financialhelper.service.TransactionsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Implementation of service TransactionsService.
 *
 * @author Alexandr Boldyrev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionsServiceImpl implements TransactionsService {

    private final TransactionsRepository repository;

    private final TransactionCategoriesService categoriesService;

    private final TransactionMapper mapper;

    @Override
    public Mono<Void> saveTransactions(List<TransactionDto> transactions) {
        log.debug("Saving transactions: {}", transactions);
        return repository.saveAll(
            transactions.stream()
                .map(mapper::toEntity)
                .toList()
        ).then();
    }

    @Override
    public Mono<Void> addTransaction(TransactionCreationDto transactionDto) {
        return categoriesService.getCategories()
                .filter(category -> category.getId().equals(transactionDto.getCategoryId()))
                .switchIfEmpty(Mono.error(new TransactionCategoryNotExistsException(
                        "Transaction category %s does not exists.".formatted(transactionDto.getCategoryId()))))
                .then(repository.save(mapper.fromTransactionCreationDto(transactionDto)))
                .then();
    }
}
