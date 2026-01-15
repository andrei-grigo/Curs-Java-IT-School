package com.reseatvation.exception;

import com.reseatvation.dto.NextSlotResult;
import lombok.Getter;

@Getter
public class ScreeningConflictException extends RuntimeException {

    private final NextSlotResult next;

    public ScreeningConflictException(String message, NextSlotResult next) {
        super(message);
        this.next = next;
    }

}
