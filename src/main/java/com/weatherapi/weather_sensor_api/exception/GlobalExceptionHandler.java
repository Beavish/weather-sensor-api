package com.weatherapi.weather_sensor_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        // Field-level errors
        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        // Global (class-level) errors
        ex.getBindingResult().getGlobalErrors()
                .forEach(error ->
                        errors.put(error.getObjectName(), error.getDefaultMessage())
                );

        log.warn("Validation failed. errors={}, message={}", errors, ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                400,
                "Validation Failed",
                "Request validation failed",
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    // 2. Handle business logic errors
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {

        log.warn("Bad request: {}", ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                400,
                "Bad Request",
                ex.getMessage(),
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

    // 3. Catch-all fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        log.error("Unexpected server error", ex);

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                500,
                "Internal Server Error",
                "Something went wrong",
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadJson(Exception ex) {

        log.warn("Malformed JSON or invalid enum: {}", ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                400,
                "Bad Request",
                "Malformed JSON or invalid field value",
                null
        );

        return ResponseEntity.badRequest().body(response);
    }
}