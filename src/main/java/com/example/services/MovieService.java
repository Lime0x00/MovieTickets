package com.example.services;

import com.example.models.Movie;
import com.example.models.ScreenTime;
import com.example.models.Movie.Genre;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MovieService extends BaseService<Movie> {

    public MovieService() {
        super(Movie.class);
    }

    /**
     * Find movies by genre
     */
    public List<Movie> findMoviesByGenre(Genre genre) {
        Map<String, Object> params = new HashMap<>();
        params.put("genre", genre);
        return findAllWhere("genre = :genre", params);
    }

    /**
     * Check if a movie has any screen times
     */
    public boolean hasScreenTime(Integer movieId) {
        if (movieId == null) return false;

        String jpql = "SELECT COUNT(s) FROM ScreenTime s WHERE s.movie.id = :movieId";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("movieId", movieId)
                .getSingleResult();
        return count > 0;
    }

    /**
     * Get all screen times for a specific movie
     */
    public List<ScreenTime> getScreenTimesForMovie(Integer movieId) {
        if (movieId == null) return List.of();

        String jpql = "SELECT s FROM ScreenTime s WHERE s.movie.id = :movieId";
        return em.createQuery(jpql, ScreenTime.class)
                .setParameter("movieId", movieId)
                .getResultList();
    }

    /**
     * Add a new screen time to a movie
     */
    public boolean addScreenTimeToMovie(Integer movieId, ScreenTime screenTime) {
        if (movieId == null || screenTime == null) return false;

        try {
            runInTransaction(em -> {
                Movie movie = em.find(Movie.class, movieId);
                if (movie == null) throw new IllegalArgumentException("Movie not found");

                screenTime.setMovie(movie);
                em.persist(screenTime);
            });
            return true;
        } catch (Exception e) {
            System.err.println("Error adding screen time: " + e.getMessage());
            return false;
        }
    }

    /**
     * Remove a screen time from a movie
     */
    public boolean removeScreenTime(Integer movieId, ScreenTime screenTime) {
        if (movieId == null || screenTime == null) return false;

        try {
            runInTransaction(em -> {
                ScreenTime managed = em.find(ScreenTime.class, screenTime.getId());
                if (managed == null || managed.getMovie() == null || !managed.getMovie().getId().equals(movieId)) {
                    throw new IllegalArgumentException("ScreenTime does not match movie");
                }
                em.remove(managed);
            });
            return true;
        } catch (Exception e) {
            System.err.println("Error removing screen time: " + e.getMessage());
            return false;
        }
    }

    /**
     * Count the number of screen times for a movie
     */
    public int countScreenTimes(Integer movieId) {
        if (movieId == null) return 0;

        String jpql = "SELECT COUNT(s) FROM ScreenTime s WHERE s.movie.id = :movieId";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("movieId", movieId)
                .getSingleResult();
        return count.intValue();
    }

    /**
     * Find all movies that have at least one screen time
     */
    public List<Movie> findMoviesWithScreenTimes() {
        String jpql = "SELECT DISTINCT s.movie FROM ScreenTime s";
        return em.createQuery(jpql, Movie.class).getResultList();
    }

    /**
     * Find all movies that have no screen times
     */
    public List<Movie> findMoviesWithoutScreenTimes() {
        String jpql = "SELECT m FROM Movie m WHERE m.screenTimes IS EMPTY";
        return em.createQuery(jpql, Movie.class).getResultList();
    }
}
