package com.reseatvation.dto;

import java.time.LocalDateTime;

public record NextSlotResult(
        boolean available,
        LocalDateTime suggestedStart,
        LocalDateTime suggestedEnd,
        String reason
) {
    public static NextSlotResult found(LocalDateTime start, LocalDateTime end) {
        return new NextSlotResult(true, start, end, null);
    }

}
