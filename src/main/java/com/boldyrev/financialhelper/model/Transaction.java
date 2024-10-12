package com.boldyrev.financialhelper.model;

import com.boldyrev.financialhelper.enums.TransactionType;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * //todo add descriprion
 *
 * @author Alexandr Boldyrev
 */
@Data
@Table("transactions")
public class Transaction {

    @Id
    @Column("transaction_id")
    private UUID id;

    @Column("category_id")
    private UUID categoryId;

    @Column("user_id")
    private Long userId;

    @Column("transaction_type")
    private TransactionType transactionType;

    @Column("amount")
    private Double amount;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @LastModifiedDate
    @Column("modified_at")
    private Instant modifiedAt;
}
