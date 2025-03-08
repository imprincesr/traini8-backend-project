package com.traini8.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the Traini8 application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation errors from invalid input data.
     *
     * @param ex The validation exception.
     * @return A response with field-specific error messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        logger.info("Validation failed: {}", ex.getMessage()); // Downgraded to info since service logs too
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        ErrorResponse error = new ErrorResponse("Validation failed", errors.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles duplicate center code errors.
     *
     * @param ex The duplicate code exception.
     * @return A response indicating a conflict.
     */
    @ExceptionHandler(DuplicateCenterCodeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCenterCode(DuplicateCenterCodeException ex) {
        logger.info("Duplicate center code error: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("Duplicate center code", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * Handles invalid training center input errors.
     *
     * @param ex The invalid input exception.
     * @return A response indicating bad request.
     */
    @ExceptionHandler(InvalidTrainingCenterException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCenter(InvalidTrainingCenterException ex) {
        logger.info("Invalid training center: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse("Invalid input", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles unexpected generic exceptions.
     *
     * @param ex The caught exception.
     * @return A response indicating an internal server error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse("Internal server error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Standard error response structure with timestamp
    private static class ErrorResponse {
        private final String message;
        private final String details;
        private final String timestamp;

        public ErrorResponse(String message, String details) {
            this.message = message;
            this.details = details;
            this.timestamp = Instant.now().toString();
        }

        public String getMessage() { return message; }
        public String getDetails() { return details; }
        public String getTimestamp() { return timestamp; }
    }
}