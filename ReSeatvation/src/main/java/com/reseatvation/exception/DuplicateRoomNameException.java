package com.reseatvation.exception;

public class DuplicateRoomNameException extends RuntimeException {

    public DuplicateRoomNameException(String name) {
        super("Room name already exists: " + name);
    }
}
