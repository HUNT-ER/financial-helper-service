package com.boldyrev.financialhelper.service;

import com.boldyrev.dto.SpendingLimitCreationDto;
import com.boldyrev.financialhelper.repository.projection.SpendingLimitCategoryProjection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

/**
 * Service for operations with {@link com.boldyrev.financialhelper.model.SpendingLimit}.
 *
 * @author Alexandr Boldyrev
 */
public interface SpendingLimitsService {

    /**
     * Saving new spending limit.
     *
     * @param spendingLimit new limit.
     * @return void
     */
    Mono<Void> addSpendingLimit(SpendingLimitCreationDto spendingLimit);

    /**
     * Get all active limits;
     *
     * @param userId user id;
     * @param period instant of current time;
     * @return active limits;
     */
    Flux<SpendingLimitCategoryProjection> getActiveLimits(Long userId, Instant period);
}
