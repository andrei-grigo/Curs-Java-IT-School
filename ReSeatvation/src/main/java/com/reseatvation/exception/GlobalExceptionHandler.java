package com.reseatvation.exception;

import com.reseatvation.dto.NextSlotResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateRoomNameException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateRoom(DuplicateRoomNameException ex) {
        return new ApiError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                "ROOM_NAME_ALREADY_EXISTS",
                ex.getMessage()
        );
    }

    @ExceptionHandler(RoomNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleRoomNotFound(RoomNotFoundException ex) {
        return new ApiError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "ROOM_NOT_FOUND",
                ex.getMessage()
        );
    }

    @ExceptionHandler(DuplicateMovieTitleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateMovie(DuplicateMovieTitleException ex) {
        return new ApiError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                "MOVIE_TITLE_ALREADY_EXISTS",
                ex.getMessage()
        );
    }

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleMovieNotFound(MovieNotFoundException ex) {
        return new ApiError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "MOVIE_NOT_FOUND",
                ex.getMessage()
        );
    }

    @ExceptionHandler(ScreeningNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleScreeningNotFound(ScreeningNotFoundException ex) {
        return new ApiError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                "SCREENING_NOT_FOUND",
                ex.getMessage()
        );
    }

    @ExceptionHandler(ScreeningConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ScreeningConflictError handleScreeningConflict(ScreeningConflictException ex) {
        ApiError api = new ApiError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                "SCREENING_CONFLICT",
                ex.getMessage()
        );
        return new ScreeningConflictError(api, ex.getNext());
    }

    @ExceptionHandler(InvalidStartTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidStartTime(InvalidStartTimeException ex) {
        return new ApiError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_START_TIME",
                ex.getMessage()
        );
    }


    @ExceptionHandler(MovieHasScreeningsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleMovieHasScreenings(MovieHasScreeningsException ex) {
        return new ApiError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                "MOVIE_HAS_SCREENINGS",
                ex.getMessage()
        );
    }

    @ExceptionHandler(RoomHasScreeningsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleRoomHasScreenings(RoomHasScreeningsException ex) {
        return new ApiError(
                Instant.now(),
                HttpStatus.CONFLICT.value(),
                "ROOM_HAS_SCREENINGS",
                ex.getMessage()
        );
    }

    public record ScreeningConflictError(
            ApiError error,
            NextSlotResult nextSlot
    ) {
    }
}
