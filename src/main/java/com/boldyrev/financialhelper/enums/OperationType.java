package com.boldyrev.financialhelper.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Operation type of receipts.
 * <p>
 * EXCHANGE category can't be processed because it's not income or expense transaction.
 *
 * @author Alexandr Boldyrev
 */
@Getter
@RequiredArgsConstructor
public enum OperationType {

    PURCHASE(1, true, true),
    RETURN(2, false, true),
    EXCHANGE(3, false, false),
    ADVANCE(4, true, true),
    ADDITIONAL_SERVICE(5, true, true),
    WRITE_OFF(6, true, true);

    private final int code;
    private final boolean isExpense;
    private final boolean isTransaction;

    @JsonCreator
    public static OperationType fromCode(int code) {
        for (OperationType type : OperationType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        return null;
    }
}