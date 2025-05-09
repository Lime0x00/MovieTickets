package com.example.views.cli;

import com.example.cli.Prompt;
import com.example.controllers.HallController;
import com.example.controllers.MovieController;
import com.example.models.*;
import com.example.utils.DateUtil;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MovieView extends View {
    private final MovieController movieCtrl;
    private final HallController hallCtrl;
    private final Scanner scanner;

    public MovieView(MovieController movieCtrl, HallController hallCtrl) {
        this.movieCtrl = movieCtrl;
        this.hallCtrl = hallCtrl;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n== Movie Menu ==");
            System.out.println("1. Add Movie");
            System.out.println("2. List Movies");
            System.out.println("3. Delete Movie");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> addMovie();
                case 2 -> listMovies();
                case 3 -> deleteMovie();
                case 4 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void listMovies() {
        List<Movie> movies = movieCtrl.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies found.");
        } else {
            System.out.println("\nAvailable Movies:");
            movies.forEach(movie ->
                    System.out.println(movie.getId() + ": " + movie.getTitle() + " [" + movie.getGenre() + "]"));
        }
    }

    private void addMovie() {
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine().trim();

        List<Movie.Genre> genres = List.of(Movie.Genre.values());
        Movie.Genre genre = Prompt.promptForSelection(scanner, "Select a genre:", genres);

        System.out.print("Enter movie duration (in hours, e.g. 1.5): ");
        float duration = Float.parseFloat(scanner.nextLine().trim());

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setGenre(genre);
        movie.setDuration(duration);

        if (!movieCtrl.addMovie(movie)) {
            System.out.println("Failed to add the movie.");
            return;
        }

        System.out.println("Movie added: " + title + " [" + genre + "]");
        addScreenTime(movie);
    }

    private void addScreenTime(Movie movie) {
        List<Hall> halls = hallCtrl.getAllHalls();
        List<ScreenTime> addedScreenTimes = new ArrayList<>();

        while (Prompt.promptYesNo(scanner, "Would you like to add a screening time for this movie? (y/n)") == Prompt.Response.YES) {
            Hall hall = Prompt.promptForSelection(scanner, "Select a hall:", halls);

            Instant startDate = DateUtil.toInstant(Prompt.promptForDate(scanner, "Enter start date/time (yyyy-MM-dd HH:mm): "));
            Instant endDate = DateUtil.toInstant(Prompt.promptForDate(scanner, "Enter end date/time (yyyy-MM-dd HH:mm): "));

            if (endDate.isBefore(startDate)) {
                System.out.println("Error: End date must be after start date.");
                continue;
            }

            boolean isDuplicate = addedScreenTimes.stream().anyMatch(st ->
                    st.getHall().equals(hall) &&
                            st.getStartDate().equals(startDate) &&
                            st.getEndDate().equals(endDate));

            if (isDuplicate) {
                System.out.println("Error: Duplicate screening time in this hall.");
                continue;
            }

            ScreenTime screenTime = new ScreenTime();
            screenTime.setMovie(movie);
            screenTime.setHall(hall);
            screenTime.setStartDate(startDate);
            screenTime.setEndDate(endDate);

            if (movieCtrl.addScreenTime(screenTime)) {
                addedScreenTimes.add(screenTime);
                System.out.println("Screening added: " + startDate + " to " + endDate + " in hall " + hall.getId());
            } else {
                System.out.println("Failed to add screening time.");
            }
        }

        System.out.println("Finished adding " + addedScreenTimes.size() + " screening(s).");
    }

    private void deleteMovie() {
        List<Movie> movies = movieCtrl.getAllMovies();
        if (movies.isEmpty()) return;

        Movie movieToDelete = Prompt.promptForSelection(scanner, "Select a movie to delete:", movies);
        if (movieCtrl.deleteMovie(movieToDelete)) {
            System.out.println("Movie deleted: " + movieToDelete.getTitle());
        } else {
            System.out.println("Failed to delete movie.");
        }
    }
}
