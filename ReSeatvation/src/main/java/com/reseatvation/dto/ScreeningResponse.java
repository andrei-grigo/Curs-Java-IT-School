package com.reseatvation.dto;

import java.time.LocalDateTime;

public record ScreeningResponse(
        Long id,
        Long roomId,
        String roomName,
        Long movieId,
        String movieTitle,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
