package com.example.views.cli;

import com.example.cli.Prompt;
import com.example.controllers.MovieController;
import com.example.controllers.ScreenTimeController;
import com.example.models.Movie;
import com.example.models.ScreenTime;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class ScreenTimeView extends View {
    private final MovieController movieCtrl;
    private final ScreenTimeController screenTimeCtrl;
    private final Scanner scanner;

    public ScreenTimeView(MovieController movieCtrl, ScreenTimeController screenTimeCtrl) {
        this.movieCtrl = movieCtrl;
        this.screenTimeCtrl = screenTimeCtrl;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n== Screen Time Menu ==");
            System.out.println("1. List All Screen Times");
            System.out.println("2. Delete Screen Time");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");

            int option = Prompt.promptIntInRange(scanner, "", 1, 3);

            switch (option) {
                case 1 -> listScreenTimes();
                case 2 -> deleteScreenTime();
                case 3 -> {
                    System.out.println("Exiting Screen Time Menu.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * List all screen times for all movies.
     */
    private void listScreenTimes() {
        List<Movie> movies = movieCtrl.getAllMovies();
        boolean found = false;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Movie movie : movies) {
            List<ScreenTime> screenTimes = screenTimeCtrl.getScreenTimesByMovie(movie);
            if (!screenTimes.isEmpty()) {
                System.out.println("\nMovie: " + movie.getTitle());
                for (ScreenTime st : screenTimes) {
                    System.out.println(" - Hall: " + st.getHall().getId() +
                            ", From: " + formatter.format(st.getStartDate().atZone(java.time.ZoneId.systemDefault())) +
                            " To: " + formatter.format(st.getEndDate().atZone(java.time.ZoneId.systemDefault())));
                }
                found = true;
            }
        }

        if (!found) {
            System.out.println("No screening times found.");
        }
    }

    /**
     * Prompt user to delete a screen time from a selected movie.
     */
    private void deleteScreenTime() {
        List<Movie> movies = movieCtrl.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies found.");
            return;
        }

        Movie movie = Prompt.promptForSelection(scanner, "Select a movie:", movies);
        List<ScreenTime> screenTimes = screenTimeCtrl.getScreenTimesByMovie(movie);

        if (screenTimes.isEmpty()) {
            System.out.println("No screen times found for this movie.");
            return;
        }

        ScreenTime toDelete = Prompt.promptForSelection(scanner, "Select a screen time to delete:", screenTimes);
        boolean success = screenTimeCtrl.deleteScreenTime(toDelete);

        System.out.println(success ? "Screen time deleted." : "Failed to delete screen time.");
    }
}
