package com.example.views.cli;

import com.example.cli.Prompt;
import com.example.controllers.HallController;
import com.example.models.Hall;
import com.example.models.ScreenTime;
import com.example.models.Seat;

import java.util.List;
import java.util.Scanner;

public class HallView extends View {
    private final HallController hallCtrl;
    private final Scanner scanner;

    public static final String[] menu = {
            "1. Add Hall",
            "2. List Halls",
            "3. Delete Hall",
            "4. Exit",
    };

    public HallView(HallController hallCtrl) {
        this.hallCtrl = hallCtrl;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            for (String choice : menu) {
                System.out.println(choice);
            }

            int option = Prompt.promptIntInRange(scanner, "Choose An Option: ", 1, 4);

            switch (option) {
                case 1 -> addHall();
                case 2 -> listHalls();
                case 3 -> deleteHall();
                case 4 -> {
                    System.out.println("Exiting Hall Menu.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void addHall() {
        int numberOfRows = Prompt.promptIntInRange(scanner, "Enter hall capacity (10–1000):", 10, 1000);
        int numberOfCols = Prompt.promptIntInRange(scanner, "Enter hall cols (10–1000):", 10, 1000);

        Hall hall = new Hall();
        hall.setNumberOfRows(numberOfRows);
        hall.setNumberOfCols(numberOfCols);

        if (hallCtrl.addHall(hall)) {
            System.out.println("Hall added successfully: " + hall);
        } else {
            System.out.println("Failed to add hall.");
        }
    }

    private void listHalls() {
        List<Hall> halls = hallCtrl.getAllHalls();
        if (halls.isEmpty()) {
            System.out.println("No halls found.");
        } else {
            System.out.println("\nAvailable Halls:");
            halls.forEach(System.out::println);
        }
    }

    private void deleteHall() {
        List<Hall> halls = hallCtrl.getAllHalls();
        if (halls.isEmpty()) {
            System.out.println("No halls available to delete.");
            return;
        }

        Hall hallToDelete = Prompt.promptForSelection(scanner, "Select a hall to delete:", halls);

        boolean hasRelatedData = checkForRelatedData(hallToDelete);
        if (hasRelatedData) {
            System.out.println("You cannot delete this hall because there is related data (screenings or seats) associated with it.");
            System.out.println("Please delete the related data first.");
        } else {
            if (hallCtrl.deleteHall(hallToDelete)) {
                System.out.println("Hall deleted successfully.");
            } else {
                System.out.println("Failed to delete hall.");
            }
        }
    }

    private boolean checkForRelatedData(Hall hall) {
        List<ScreenTime> screenTimes = hallCtrl.getScreenTimesForHall(hall);
        List<Seat> seats = hallCtrl.getSeatsForHall(hall);
        return !screenTimes.isEmpty() || !seats.isEmpty();
    }
}
