package com.example.services;

import com.example.models.Hall;
import com.example.models.Movie;
import com.example.models.ScreenTime;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScreenTimeService extends BaseService<ScreenTime> {

    public ScreenTimeService() {
        super(ScreenTime.class);
    }

    /**
     * Find all screen times for a given movie
     */
    public List<ScreenTime> findByMovie(Movie movie) {
        if (movie == null) return List.of();

        String condition = "t.movie = :movie";
        Map<String, Object> params = new HashMap<>();
        params.put("movie", movie);
        return findAllWhere(condition, params);
    }

    /**
     * Find all screen times for a given hall
     */
    public List<ScreenTime> findByHall(Hall hall) {
        if (hall == null) return List.of();

        String condition = "t.hall = :hall";
        Map<String, Object> params = new HashMap<>();
        params.put("hall", hall);
        return findAllWhere(condition, params);
    }
}
