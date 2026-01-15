package com.reseatvation.exception;

import java.time.LocalDateTime;

public class InvalidStartTimeException extends RuntimeException {
    public InvalidStartTimeException(LocalDateTime startTime) {
        super("Start time must be aligned to 5-minute intervals (e.g., 19:00, 19:05...). Provided: " + startTime);
    }
}
