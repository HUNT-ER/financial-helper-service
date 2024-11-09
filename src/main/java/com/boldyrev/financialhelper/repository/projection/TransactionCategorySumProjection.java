package com.boldyrev.financialhelper.repository.projection;

import java.util.UUID;

/**
 * Projection of total sum of all user categories.
 *
 * @author Александр Болдырев
 */
public interface TransactionCategorySumProjection {

    UUID getCategoryId();

    Long getUserId();

    Double getAmount();
}
