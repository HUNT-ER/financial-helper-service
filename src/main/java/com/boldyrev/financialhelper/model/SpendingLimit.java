package com.boldyrev.financialhelper.model;

import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Representation of spending limit by user and category.
 *
 * @author Alexandr Boldyrev
 */
@Data
@Table("spending_limits")
public class SpendingLimit {

    @Id
    @Column("limit_id")
    private UUID id;

    @Column("category_id")
    private UUID categoryId;

    @Column("user_id")
    private Long userId;

    @Column("spending_limit")
    private Double spendingLimit;

    @Column("start_period")
    private Instant startPeriod;

    @Column("end_period")
    private Instant endPeriod;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column("modified_at")
    private Instant modifiedAt;
}
