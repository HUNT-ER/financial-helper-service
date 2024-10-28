package com.boldyrev.financialhelper.model;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Representation of transaction category.
 *
 * @author Alexandr Boldyrev
 */
@Data
@Table("transaction_categories")
public class TransactionCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("category_id")
    private UUID id;

    @Column("category_name")
    private String categoryName;

    @Column("is_expense")
    private boolean isExpense;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column("modified_at")
    private Instant modifiedAt;
}
