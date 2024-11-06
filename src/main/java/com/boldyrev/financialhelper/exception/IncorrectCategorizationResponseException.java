package com.boldyrev.financialhelper.exception;

/**
 * Exception thrown when errors occur during categorization.
 *
 * @author Alexandr Boldyrev
 */
public class IncorrectCategorizationResponseException extends RuntimeException {

    public IncorrectCategorizationResponseException(String message) {
        super(message);
    }
}
