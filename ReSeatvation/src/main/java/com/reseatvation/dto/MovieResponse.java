package com.reseatvation.dto;

public record MovieResponse(
        Long id,
        String title,
        int durationMinutes
) {
}
