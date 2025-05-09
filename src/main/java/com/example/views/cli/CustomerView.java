package com.example.views.cli;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.example.cli.Prompt;
import com.example.controllers.CustomerController;
import com.example.controllers.MovieController;
import com.example.controllers.ScreenTimeController;
import com.example.controllers.SeatController;
import com.example.models.*;

public class CustomerView extends View {
    private final CustomerController customerCtrl;
    private final MovieController movieCtrl;
    private final SeatController seatCtrl;
    private final ScreenTimeController screenTimeCtrl;
    private final Scanner scanner;

    public CustomerView(CustomerController customerCtrl, MovieController movieCtrl, SeatController seatCtrl, ScreenTimeController screenTimeCtrl) {
        this.customerCtrl = customerCtrl;
        this.movieCtrl = movieCtrl;
        this.seatCtrl = seatCtrl;
        this.screenTimeCtrl = screenTimeCtrl;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n== Customer Menu ==");
            System.out.println("1. Login as Customer");
            System.out.println("2. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> login();
                case 2 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option, please try again.");
            }
        }
    }

    private void login() {
        List<Customer> customers = customerCtrl.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        Customer customer = Prompt.promptForSelection(scanner, "Select a customer to login as:", customers);
        System.out.println("Logged in as: " + customer.getName());

        while (true) {
            System.out.println("\n== " + customer.getName() + "'s Dashboard ==");
            System.out.println("1. Book a Movie");
            System.out.println("3. Logout");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> bookMovieForCustomer(customer);
                case 3 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void bookMovieForCustomer(Customer customer) {
        List<Movie> movies = movieCtrl.getAllMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
            return;
        }

        Movie movie = Prompt.promptForSelection(scanner, "Select a movie:", movies);
        List<ScreenTime> screenTimes = movieCtrl.getScreenTimes(movie);
        if (screenTimes.isEmpty()) {
            System.out.println("No screenings available for this movie.");
            return;
        }

        ScreenTime screenTime = Prompt.promptForSelection(scanner, "Select a screening:", screenTimes);

        List<Seat> inAvailableSeats = seatCtrl.getInAvailableSeatsForScreenTime(screenTime);
        Hall hall = screenTime.getHall();

        String[][] seatLayout = new String[hall.getNumberOfRows()][hall.getNumberOfCols()];
        Set<String> inAvailableIds = inAvailableSeats.stream()
                .map(Seat::getId)
                .collect(Collectors.toSet());

        System.out.println("\n\t\t\t\tScreen");
        System.out.println("\t\txxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        System.out.print("    ");
        for (int c = 1; c <= hall.getNumberOfCols(); c++) {
            System.out.print(c + "\t");
        }
        System.out.println();

        List<Seat> availableSeats = new ArrayList<>();

        for (int r = 0; r < hall.getNumberOfRows(); r++) {
            String rowLetter = String.valueOf((char) ('A' + r));
            System.out.print(rowLetter + "\t");

            for (int c = 0; c < hall.getNumberOfCols(); c++) {
                String seatId = rowLetter + (c + 1);
                boolean isBooked = inAvailableIds.contains(seatId);
                Seat seat = new Seat();
                seat.setId(seatId);
                if (rowLetter.charAt(0) >= 'A' && rowLetter.charAt(0) <= 'C') {
                    seat.setClassField("FIRST");
                    seat.setDefaultPrice(BigDecimal.valueOf(200.0));
                } else if (rowLetter.charAt(0) >= 'D' && rowLetter.charAt(0) <= 'F') {
                    seat.setClassField("SECOND");
                    seat.setDefaultPrice(BigDecimal.valueOf(150.0));
                } else if (rowLetter.charAt(0) >= 'F' && rowLetter.charAt(0) <= 'Z') {
                    seat.setClassField("THIRD");
                    seat.setDefaultPrice(BigDecimal.valueOf(100.0));
                }
                if (isBooked) {
                    
                    System.out.print("\u001B[31mB\u001B[0m\t\t\t");
                } else {
                    
                    System.out.print("\u001B[32mF\u001B[0m("+seat.getDefaultPrice()+")\t");
                    availableSeats.add(seat);
                }
            }
            System.out.println();
        }

        if (availableSeats.isEmpty()) {
            System.out.println("No seats available for this screening.");
            return;
        }


        Set<Seat> selectedSeats = new HashSet<>();
        while (true) {
            String input = Prompt.promptString(scanner, "Enter seat ID (e.g. A1) to select (or type 'done' to finish):");
            if (input.equalsIgnoreCase("done")) {
                break;
            }

            Seat selectedSeat = availableSeats.stream()
                    .filter(seat -> seat.getId().equalsIgnoreCase(input))
                    .findFirst()
                    .orElse(null);

            if (selectedSeat != null) {
                selectedSeats.add(selectedSeat);
                System.out.println("Seat " + input + " added.");
            } else {
                System.out.println("Invalid seat ID or already booked.");
            }
        }

        if (selectedSeats.isEmpty()) {
            System.out.println("No seats selected. Booking cancelled.");
            return;
        }

        try {
            customerCtrl.bookMovie(customer, screenTime, selectedSeats);
        } catch (IllegalArgumentException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }

 
}
