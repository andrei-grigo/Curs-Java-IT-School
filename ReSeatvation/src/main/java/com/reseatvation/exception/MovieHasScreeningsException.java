package com.reseatvation.exception;

public class MovieHasScreeningsException extends RuntimeException {
    public MovieHasScreeningsException(Long movieId) {
        super("Cannot delete movie. It still has screenings. Movie id: " + movieId);
    }
}
