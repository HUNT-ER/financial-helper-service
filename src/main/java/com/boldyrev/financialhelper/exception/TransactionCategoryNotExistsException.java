package com.boldyrev.financialhelper.exception;

/**
 * Exception thrown when transaction category does not exist.
 *
 * @author Alexandr Boldyrev
 */
public class TransactionCategoryNotExistsException extends RuntimeException {

    public TransactionCategoryNotExistsException(String message) {
        super(message);
    }
}
