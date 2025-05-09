package com.example.controllers;

import com.example.models.Movie;
import com.example.models.ScreenTime;
import com.example.services.MovieService;
import com.example.services.ScreenTimeService;

import java.util.List;
import java.util.Optional;

public class MovieController {
    private final MovieService movieService;
    private final ScreenTimeService screenTimeService;
    public MovieController(MovieService movieService, ScreenTimeService screenTimeService) {
        this.movieService = movieService;
        this.screenTimeService = screenTimeService;
    }

    public boolean addMovie(Movie movie) {
        if (movie == null) return false;
        return movieService.save(movie);
    }

    public boolean deleteMovie(Movie movie) {
        if (movie == null || movie.getId() == null) return false;
        if (!movieService.exists(movie.getId())) return false;

        if (movieService.hasScreenTime(movie.getId())) {
            return false;  
        }

        return movieService.delete(movie);
    }

    public boolean addScreenTime(ScreenTime screenTime) {
        if (screenTime == null || screenTime.getMovie() == null) return false;

        int movieId = screenTime.getMovie().getId();
        List<ScreenTime> existingTimes = movieService.getScreenTimesForMovie(movieId);

        boolean duplicate = existingTimes.stream()
                .anyMatch(st -> st.getId().equals(screenTime.getId()));
        if (duplicate) return false;

        return movieService.addScreenTimeToMovie(movieId, screenTime);
    }

    public List<ScreenTime> getScreenTimes(Movie movie) {
        return movieService.getScreenTimesForMovie(movie.getId());
    }

    public List<Movie> getAllMovies() {
        return movieService.findAll();
    }

    public boolean exists(Movie movie) {
        return movieService.exists(movie.getId());
    }
}
