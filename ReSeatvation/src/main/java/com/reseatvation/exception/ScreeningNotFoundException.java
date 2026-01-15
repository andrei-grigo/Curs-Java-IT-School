package com.reseatvation.exception;

public class ScreeningNotFoundException extends RuntimeException {
    public ScreeningNotFoundException(Long id) {
        super("Screening not found: " + id);
    }
}
