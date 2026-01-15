package com.reseatvation.exception;

public class DuplicateMovieTitleException extends RuntimeException {
    public DuplicateMovieTitleException(String title) {
        super("Movie title already exists: " + title);
    }
}
