package com.boldyrev.financialhelper.repository;

import com.boldyrev.financialhelper.model.SpendingLimit;
import com.boldyrev.financialhelper.repository.projection.SpendingLimitCategoryProjection;
import java.time.Instant;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository for {@link SpendingLimit}.
 *
 * @author Alexandr Boldyrev
 */
public interface SpendingLimitsRepository extends ReactiveCrudRepository<SpendingLimit, UUID> {

    /**
     * Find all active {@link SpendingLimit}.
     *
     * @param userId user id
     * @return list of active {@link SpendingLimit}
     */
    @Query(value = """
        SELECT category_id,
            user_id,
            spending_limit,
            start_period,
            end_period
        FROM spending_limits
        WHERE user_id = :userId
            AND ((end_period is null and :period >= start_period)
                OR :period between start_period and end_period);
        """)
    Flux<SpendingLimitCategoryProjection> findActiveLimits(@Param("userId") Long userId,
        @Param("period") Instant period);

    /**
     * Find {@link SpendingLimit} by userId and categoryId.
     *
     * @param userId     user id
     * @param categoryId category id
     * @return found {@link SpendingLimit}
     */
    Mono<SpendingLimit> findByUserIdAndCategoryId(Long userId, UUID categoryId);
}
