package com.traini8.exception;

/**
 * Exception thrown when a duplicate center code is detected.
 */
public class DuplicateCenterCodeException extends RuntimeException {
    public DuplicateCenterCodeException(String centerCode) {
        super("Center code already exists: " + centerCode);
    }
}
