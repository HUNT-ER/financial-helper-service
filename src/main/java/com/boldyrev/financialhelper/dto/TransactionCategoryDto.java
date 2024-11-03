package com.boldyrev.financialhelper.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dto object of {@link com.boldyrev.financialhelper.model.TransactionCategory}.
 *
 * @author Alexandr Boldyrev
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCategoryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UUID id;

    private String categoryName;

    private boolean isExpense;

    private Instant createdAt;

    private Instant modifiedAt;
}
