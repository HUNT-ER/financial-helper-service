package com.boldyrev.financialhelper.exception;

/**
 * Exception thrown when errors occur during QR code reading.
 *
 * @author Alexandr Boldyrev
 */
public class QrCodeReadingException extends RuntimeException {

    /**
     * Constructor for creating a new {@code QrCodeReadingException}.
     *
     * @param message a message describing the reason for the exception
     * @param cause   the cause of the exception (the underlying reason that triggered this exception)
     */
    public QrCodeReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
