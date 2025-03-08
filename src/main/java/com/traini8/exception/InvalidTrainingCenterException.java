package com.traini8.exception;

/**
 * Exception thrown when a training center object is invalid.
 */
public class InvalidTrainingCenterException extends RuntimeException {
    public InvalidTrainingCenterException(String message) {
        super(message);
    }
}