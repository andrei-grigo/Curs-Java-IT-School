package com.reseatvation.dto;

public record RoomResponse(
        Long id,
        String name,
        int rowsCount,
        int seatsPerRow
) {
}