package com.wbt.store.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError> methodArgumentNotValidExceptionHandler(final MethodArgumentNotValidException ex, final WebRequest wr) {
        final var errorDetails = new HashMap<String, String>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errorDetails.put(fieldError.getField(), fieldError.getDefaultMessage()));
        final var apiError = new ApiError(
                HttpStatus.BAD_REQUEST.name(),
                wr.getDescription(false).split("=")[1],
                ex.getMessage(),
                LocalDateTime.now(),
                errorDetails
        );
        return ResponseEntity.badRequest().body(apiError);
    }

}
