package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.dto.TransactionCreationDto;
import com.boldyrev.dto.TransactionsFilter;
import com.boldyrev.dto.TransactionsResponse;
import com.boldyrev.financialhelper.dto.MessageDto;
import com.boldyrev.financialhelper.dto.TransactionCategoryDto;
import com.boldyrev.financialhelper.dto.TransactionDto;
import com.boldyrev.financialhelper.enums.MessageType;
import com.boldyrev.financialhelper.enums.RoutingKey;
import com.boldyrev.financialhelper.enums.TransactionType;
import com.boldyrev.financialhelper.enums.UserMessage;
import com.boldyrev.financialhelper.exception.TransactionCategoryNotExistsException;
import com.boldyrev.financialhelper.mapper.TransactionMapper;
import com.boldyrev.financialhelper.repository.TransactionsRepository;
import com.boldyrev.financialhelper.repository.projection.TransactionCategorySumProjection;
import com.boldyrev.financialhelper.service.RabbitMqMessageService;
import com.boldyrev.financialhelper.service.SpendingLimitsService;
import com.boldyrev.financialhelper.service.TransactionCategoriesService;
import com.boldyrev.financialhelper.service.TransactionsService;
import java.time.Instant;
import java.util.List;
import java.util.StringJoiner;
import java.util.UUID;
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

    private final TransactionCategoriesService categoriesService;

    private final SpendingLimitsService limitsService;

    private final RabbitMqMessageService messageService;

    private final TransactionMapper mapper;

    @Override
    public Mono<Void> saveTransactions(List<TransactionDto> transactions) {
        log.debug("Saving transactions: {}", transactions);
        return repository.saveAll(transactions.stream().map(mapper::toEntity).toList())
            .next()
            .flatMap(transaction -> checkSpendingLimit(transaction.getUserId()))
            .then();
    }

    @Override
    public Mono<Void> addTransaction(TransactionCreationDto transactionDto) {
        return categoriesService.getCategories()
            .filter(category -> category.getId().equals(transactionDto.getCategoryId()))
            .switchIfEmpty(Mono.error(new TransactionCategoryNotExistsException(
                "Transaction category %s does not exists.".formatted(
                    transactionDto.getCategoryId()))))
            .then(repository.save(mapper.fromTransactionCreationDto(transactionDto)))
            .flatMap(transaction -> checkSpendingLimit(transaction.getUserId()))
            .then();
    }

    @Override
    public Mono<TransactionsResponse> getTransactionsByFilter(Long userId,
        TransactionsFilter filter) {
        TransactionType transactionType = TransactionType.valueOf(
            filter.getTransactionType().name());

        return repository.findAllByParameters(userId, filter.getCategoryId(), transactionType,
                filter.getStartPeriod().toInstant(), filter.getEndPeriod().toInstant())
            .collectList()
            .map(mapper::toTransactionsResponse);
    }

    @Override
    public Mono<Void> deleteTransaction(UUID transactionId) {
        return repository.deleteById(transactionId);
    }

    private Mono<Void> checkSpendingLimit(Long userId) {
        return limitsService.getActiveLimits(userId, Instant.now())
            .flatMap(limit -> repository.getCategoryPeriodSummary(userId, limit.getCategoryId(),
                    limit.getStartPeriod(), limit.getEndPeriod())
                .filter(summary -> summary.getAmount() > limit.getSpendingLimit())
                .map(TransactionCategorySumProjection::getCategoryId))
            .collectList()
            .filter(categories -> !categories.isEmpty())
            .flatMap(exceededLimitCategories -> categoriesService.getCategories()
                .collectMap(TransactionCategoryDto::getId, TransactionCategoryDto::getCategoryName)
                .flatMap(categoriesMap -> Mono.just(exceededLimitCategories.stream()
                    .map(categoriesMap::get)
                    .toList())))
            .doOnNext(exceededLimitCategories -> {
                String message = UserMessage.SPENDING_LIMIT_EXCEEDED.getMessage()
                    .formatted(formatCategories(exceededLimitCategories));
                MessageDto messageDto = new MessageDto(userId, message, MessageType.NOTIFICATION,
                    null);

                messageService.sendMessage(RoutingKey.NOTIFICATION, messageDto);
            })
            .then();
    }

    private static String formatCategories(List<String> categories) {
        StringJoiner joiner = new StringJoiner(", ");
        categories.forEach(joiner::add);

        return joiner.toString();
    }
}
