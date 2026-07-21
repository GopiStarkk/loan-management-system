package com.vertexfinance.loan.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateCustomerException.class)
    public ErrorResponse handleDuplicateCustomerException(
            DuplicateCustomerException ex,
            HttpServletRequest request) {

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Duplicate Customer")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ErrorResponse handleCustomerNotFoundException(
            CustomerNotFoundException ex,
            HttpServletRequest request) {

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Customer Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(
        MethodArgumentNotValidException ex,
        HttpServletRequest request) {

    FieldError fieldError = ex.getBindingResult().getFieldError();

    String message = fieldError != null
            ? fieldError.getField() + ": " + fieldError.getDefaultMessage()
            : "Validation failed";

    return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .message(message)
            .path(request.getRequestURI())
            .build();
}
}
