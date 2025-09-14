package com.wbt.store.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException ex,
            final WebRequest request) {

        final var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach(objectError -> {
            final var field = ((FieldError) objectError).getField();
            final var errorMsg = objectError.getDefaultMessage();
            errors.put(field, errorMsg);
        });
        final var apiError = new ApiError(
                HttpStatus.BAD_REQUEST.name(),
                request.getDescription(false).split("=")[1],
                "Date constraint violation",
                LocalDateTime.now(),
                errors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler({EntityResourceNotFoundException.class})
    public ResponseEntity<ApiError> handleResourceNotFound(final EntityResourceNotFoundException ex, final WebRequest request) {
        final var apiError = new ApiError(
                HttpStatus.NOT_FOUND.name(),
                request.getDescription(false),
                "Resource not found",
                LocalDateTime.now(),
                Map.of("error", ex.getMessage()));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
}
