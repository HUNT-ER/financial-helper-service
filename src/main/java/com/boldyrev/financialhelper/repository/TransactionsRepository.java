package com.boldyrev.financialhelper.repository;

import com.boldyrev.financialhelper.model.Transaction;
import com.boldyrev.financialhelper.repository.projection.TransactionCategorySumProjection;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

/**
 * Repository for {@link Transaction}.
 *
 * @author Alexandr Boldyrev
 */
public interface TransactionsRepository extends ReactiveCrudRepository<Transaction, UUID> {

    @Query("""
        SELECT category_id,
            user_id,
            sum(amount) as amount
        FROM transactions
        where user_id = :userId
            and category_id = :categoryId
            and transaction_type = 'EXPENSE'
            and ((:endPeriod is null and transaction_date_time >= :startPeriod)
                OR (transaction_date_time BETWEEN :startPeriod and :endPeriod))
        group by category_id, user_id
        """)
    Mono<TransactionCategorySumProjection> getCategoryPeriodSummary(@Param("userId") Long userId,
                                                                    @Param("categoryId") UUID categoryId,
                                                                    @Param("startPeriod") Instant startPeriod,
                                                                    @Param("endPeriod") Instant endPeriod);
}
