package com.reseatvation.exception;

public class RoomHasScreeningsException extends RuntimeException {
    public RoomHasScreeningsException(Long roomId) {
        super("Cannot delete room. It still has screenings. Room id: " + roomId);
    }
}
