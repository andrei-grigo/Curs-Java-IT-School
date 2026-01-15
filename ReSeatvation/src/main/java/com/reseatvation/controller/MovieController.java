package com.reseatvation.controller;

import com.reseatvation.dto.CreateMovieRequest;
import com.reseatvation.dto.DeleteResponse;
import com.reseatvation.dto.MovieResponse;
import com.reseatvation.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponse create(@Valid @RequestBody CreateMovieRequest request) {
        return movieService.createMovie(request);
    }

    @GetMapping
    public List<MovieResponse> getAll() {
        return movieService.getAllMovies();
    }

    @DeleteMapping("/{id}")
    public DeleteResponse delete(@PathVariable Long id) {
        String title = movieService.deleteMovie(id);

        return new DeleteResponse(
                "Movie '" + title + "' was deleted successfully",
                Instant.now()
        );
    }

}