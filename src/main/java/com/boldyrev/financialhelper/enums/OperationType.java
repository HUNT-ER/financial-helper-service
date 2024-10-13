package com.boldyrev.financialhelper.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Operation type of receipts.
 *
 * @author Alexandr Boldyrev
 */
@Getter
@RequiredArgsConstructor
public enum OperationType {

    PURCHASE(1, TransactionType.EXPENSE),
    RETURN(2, TransactionType.INCOME),
    EXCHANGE(3, TransactionType.TRANSFER),
    ADVANCE(4, TransactionType.EXPENSE),
    ADDITIONAL_SERVICE(5, TransactionType.EXPENSE),
    WRITE_OFF(6, TransactionType.EXPENSE);

    private final int code;

    private final TransactionType transactionType;

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