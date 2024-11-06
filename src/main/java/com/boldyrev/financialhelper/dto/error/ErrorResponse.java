package com.boldyrev.financialhelper.dto.error;

import lombok.Value;
import org.springframework.http.HttpStatus;

/**
 * Client error response.
 *
 * @author Alexandr Boldyrev
 */
@Value
public class ErrorResponse {

    String message;

    HttpStatus status;
}
