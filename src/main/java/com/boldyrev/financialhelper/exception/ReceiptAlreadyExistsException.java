package com.boldyrev.financialhelper.exception;

/**
 * Exception thrown when errors occur during saving existed receipt.
 *
 * @author Alexandr Boldyrev
 */
public class ReceiptAlreadyExistsException extends RuntimeException {

    public ReceiptAlreadyExistsException(String message) {
        super(message);
    }
}
