package com.example.cli;

import java.util.*;

public class Prompt {
    public enum Response { YES, NO, UNKNOWN }

    /**
     * Normalize a raw user answer to YES, NO or UNKNOWN.
     */
    public static Response getResponse(String input) {
        if (input == null) {
            return Response.UNKNOWN;
        }
        return switch (input.trim().toLowerCase(Locale.ROOT)) {
            case "y", "yes" -> Response.YES;
            case "n", "no" -> Response.NO;
            default -> Response.UNKNOWN;
        };
    }

    /**
     * Prompt the user until they answer y/yes or n/no.
     * @return YES or NO
     */
    public static Response promptYesNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            Response resp = getResponse(scanner.nextLine());
            if (resp == Response.YES || resp == Response.NO) {
                return resp;
            }
            System.out.println("Invalid input. Please enter y or n.");
        }
    }

    /**
     * Prompt the user until they enter an integer between min and max (inclusive).
     * @return the valid integer
     */
    public static int promptIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt + " ");
            String line = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(line);
                if (choice >= min && choice <= max) {
                    return choice;
                }
            } catch (NumberFormatException ignored) {}
            System.out.printf("Please enter a number between %d and %d.%n", min, max);
        }
    }

    /**
     * Display a numbered list of options, prompt the user to pick one,
     * and return the chosen element.
     */
    public static <T> T promptForSelection(Scanner scanner, String prompt, List<T> options) {
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, options.get(i));
        }
        int choice = promptIntInRange(scanner, prompt, 1, options.size());
        return options.get(choice - 1);
    }

    /**
     * Prompt the user for a valid seat ID or custom string input.
     * @return trimmed user input
     */
    public static String promptString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    /**
     * Prompt the user for a date (with year/month/day/hour/minute).
     */
    public static Date promptForDate(Scanner scanner, String message) {
        System.out.println(message);

        int year = promptIntInRange(scanner, "Enter year (e.g., 2025):", 1900, 2100);
        int month = promptIntInRange(scanner, "Enter month (1–12):", 1, 12);
        int day = promptIntInRange(scanner, "Enter day (1–31):", 1, 31);
        int hour = promptIntInRange(scanner, "Enter hour (0–23):", 0, 23);
        int minute = promptIntInRange(scanner, "Enter minute (0–59):", 0, 59);

        Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        try {
            calendar.set(year, month - 1, day, hour, minute, 0);
            return calendar.getTime();
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Invalid date. Please try again.");
            return promptForDate(scanner, message);
        }
    }
}
