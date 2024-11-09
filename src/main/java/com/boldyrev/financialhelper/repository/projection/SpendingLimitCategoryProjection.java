package com.boldyrev.financialhelper.repository.projection;

import java.time.Instant;
import java.util.UUID;

/**
 * @author Александр Болдырев
 */
public interface SpendingLimitCategoryProjection {

    UUID getCategoryId();

    Long getUserId();

    Double getSpendingLimit();

    Instant getStartPeriod();

    Instant getEndPeriod();
}
