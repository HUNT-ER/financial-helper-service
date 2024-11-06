package com.boldyrev.financialhelper.exception.handling;

import com.boldyrev.financialhelper.dto.error.ErrorResponse;
import com.boldyrev.financialhelper.exception.TransactionCategoryNotExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.StringJoiner;

/**
 * Global exception handler.
 *
 * @author Alexandr Boldyrev
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TransactionCategoryNotExistsException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleTransactionCategoryNotExistsException(
            TransactionCategoryNotExistsException e) {
        log.debug("Trying to save not existing transaction category.", e);
        return Mono.just(ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST)));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleWebExchangeBindException(WebExchangeBindException e) {
        log.debug("Trying to pass incorrect dto arguments.", e);

        StringJoiner errors = new StringJoiner(", ");
        e.getBindingResult().getFieldErrors().forEach(
                fieldError -> errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage()));

        return Mono.just(
                ResponseEntity.badRequest().body(new ErrorResponse(errors.toString(), HttpStatus.BAD_REQUEST)));
    }
}
