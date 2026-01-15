package com.reseatvation.dto;

import java.time.Instant;

public record DeleteResponse(
        String message,
        Instant timestamp
) {
}
