package com.example.controllers;

import com.example.models.Movie;
import com.example.models.ScreenTime;
import com.example.services.ScreenTimeService;

import java.util.List;
import java.util.Set;

public class ScreenTimeController {

    private final ScreenTimeService screenTimeService;

    public ScreenTimeController(ScreenTimeService screenTimeService) {
        this.screenTimeService = screenTimeService;
    }

    /**
     * Get all screening times for a given movie.
     */
    public List<ScreenTime> getScreenTimesByMovie(Movie movie) {
        return screenTimeService.findByMovie(movie);
    }

    /**
     * Delete a given screening time.
     */
    public boolean deleteScreenTime(ScreenTime screenTime) {
        return screenTimeService.delete(screenTime);
    }
    
}
