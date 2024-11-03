package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.financialhelper.dto.TransactionDto;
import com.boldyrev.financialhelper.mapper.TransactionMapper;
import com.boldyrev.financialhelper.repository.TransactionsRepository;
import com.boldyrev.financialhelper.service.TransactionsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
}
