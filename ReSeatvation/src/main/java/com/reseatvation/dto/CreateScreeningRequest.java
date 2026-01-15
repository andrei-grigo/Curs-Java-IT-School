package com.reseatvation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateScreeningRequest(
        @NotNull Long roomId,
        @NotNull Long movieId,
        @NotNull @Future LocalDateTime startTime
) {
}
