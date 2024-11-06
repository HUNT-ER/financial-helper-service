package com.boldyrev.financialhelper.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

/**
 * Messages to user.
 *
 * @author Alexandr Boldyrev
 */
@RequiredArgsConstructor
public enum UserMessage {
    RECEIPT_ALREADY_EXISTS("Error: You are trying to add an existing receipt."),
    INCORRECT_QR_CODE("Incorrect QR-code."),
    CATEGORIZATION_ERROR("Can't recognize QR-code. Try again later."),
    SUCCESS_QR_CODE_READING("QR-code successfully read.");

    private final String message;

    @JsonValue
    public String getMessage() {
        return message;
    }
}
