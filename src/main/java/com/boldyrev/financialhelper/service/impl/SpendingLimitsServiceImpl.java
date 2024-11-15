package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.dto.SpendingLimitCreationDto;
import com.boldyrev.financialhelper.exception.TransactionCategoryNotExistsException;
import com.boldyrev.financialhelper.mapper.SpendingLimitMapper;
import com.boldyrev.financialhelper.repository.SpendingLimitsRepository;
import com.boldyrev.financialhelper.repository.projection.SpendingLimitCategoryProjection;
import com.boldyrev.financialhelper.service.SpendingLimitsService;
import com.boldyrev.financialhelper.service.TransactionCategoriesService;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link SpendingLimitsService}.
 *
 * @author Alexandr Boldyrev
 */
@Service
@RequiredArgsConstructor
public class SpendingLimitsServiceImpl implements SpendingLimitsService {

    private final SpendingLimitsRepository limitsRepository;

    private final TransactionCategoriesService categoriesService;

    private final SpendingLimitMapper mapper;

    private final TransactionalOperator r2dbcTransactionalOperator;

    @Override
    public Mono<Void> addSpendingLimit(SpendingLimitCreationDto limitDto) {
        return categoriesService.getCategories()
            .filter(category -> category.getId().equals(limitDto.getCategoryId()))
            .switchIfEmpty(Mono.error(new TransactionCategoryNotExistsException(
                "Transaction category %s does not exists.".formatted(limitDto.getCategoryId()))))
            .then(limitsRepository.findByUserIdAndCategoryId(limitDto.getUserId(),
                limitDto.getCategoryId()))
            .flatMap(existedLimit -> Mono.just(mapper.updateSpendingLimit(existedLimit, limitDto)))
            .switchIfEmpty(Mono.just(mapper.fromSpendingLimitCreationDto(limitDto)))
            .flatMap(limitsRepository::save)
            .as(r2dbcTransactionalOperator::transactional)
            .then();
    }

    @Override
    public Flux<SpendingLimitCategoryProjection> getActiveLimits(Long userId, Instant period) {
        return limitsRepository.findActiveLimits(userId, period);
    }

    @Override
    public Mono<Void> deleteLimit(Long userId, UUID categoryId) {
        return limitsRepository.deleteByUserIdAndCategoryId(userId, categoryId)
            .as(r2dbcTransactionalOperator::transactional);
    }
}
