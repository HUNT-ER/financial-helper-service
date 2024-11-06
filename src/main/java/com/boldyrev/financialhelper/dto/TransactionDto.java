package com.boldyrev.financialhelper.dto;

import com.boldyrev.financialhelper.enums.TransactionType;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Dto object of {@link com.boldyrev.financialhelper.model.Transaction}.
 *
 * @author Alexandr Boldyrev
 */
@Getter
@Builder
@ToString
public class TransactionDto {

    private UUID id;

    private UUID categoryId;

    private Long userId;

    private TransactionType transactionType;

    private Double amount;

    private Instant transactionDateTime;

    private Instant createdAt;

    private Instant modifiedAt;
}
