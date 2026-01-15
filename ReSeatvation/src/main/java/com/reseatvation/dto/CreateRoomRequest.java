package com.reseatvation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateRoomRequest(
        @NotBlank String name,
        @Min(1) int rowsCount,
        @Min(1) int seatsPerRow
) {
}