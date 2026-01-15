package com.reseatvation.service;

import com.reseatvation.dto.CreateMovieRequest;
import com.reseatvation.dto.MovieResponse;
import com.reseatvation.entity.Movie;
import com.reseatvation.exception.DuplicateMovieTitleException;
import com.reseatvation.exception.MovieHasScreeningsException;
import com.reseatvation.exception.MovieNotFoundException;
import com.reseatvation.repository.MovieRepository;
import com.reseatvation.repository.ScreeningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ScreeningRepository screeningRepository;

    public MovieService(MovieRepository movieRepository, ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
    }

    public MovieResponse createMovie(CreateMovieRequest request) {
        movieRepository.findByTitle(request.title())
                .ifPresent(m -> {
                    throw new DuplicateMovieTitleException(request.title());
                });

        Movie saved = movieRepository.save(new Movie(request.title(), request.durationMinutes()));
        return toResponse(saved);
    }

    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public String deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));

        if (screeningRepository.existsByMovieId(id)) {
            throw new MovieHasScreeningsException(id);
        }

        movieRepository.delete(movie);
        return movie.getTitle();
    }


    private MovieResponse toResponse(Movie movie) {
        return new MovieResponse(movie.getId(), movie.getTitle(), movie.getDurationMinutes());
    }
}
