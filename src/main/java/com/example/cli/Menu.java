package com.example.cli;

import com.example.controllers.*;
import com.example.services.*;
import com.example.views.cli.CustomerView;
import com.example.views.cli.HallView;
import com.example.views.cli.MovieView;
import com.example.views.cli.ScreenTimeView;

import java.util.Scanner;

public class Menu {

    private final CustomerView customerView;
    private final MovieView movieView;
    private final HallView hallView;
    private final ScreenTimeView screenTimeView;

    public Menu() {
        // Services
        ScreenTimeService screenTimeService = new ScreenTimeService();
        MovieService movieService = new MovieService();
        HallService hallService = new HallService();
        SeatService seatService = new SeatService();
        CustomerService customerService = new CustomerService();
        ReceiptService receiptService = new ReceiptService();

        // Controllers
        ScreenTimeController screenTimeController = new ScreenTimeController(screenTimeService);
        MovieController movieController = new MovieController(movieService, screenTimeService);
        HallController hallController = new HallController(hallService, seatService, screenTimeService);
        CustomerController customerController = new CustomerController(customerService, receiptService, screenTimeService);
        SeatController seatController = new SeatController(seatService);

        // Views
        this.customerView = new CustomerView(customerController, movieController, seatController, screenTimeController);
        this.movieView = new MovieView(movieController, hallController);
        this.hallView = new HallView(hallController);
        this.screenTimeView = new ScreenTimeView(movieController, screenTimeController);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            customerView.showMenu();
            displayMainMenu();
            int option = Prompt.promptIntInRange(scanner, "Choose An Option: ", 1, 4);

            switch (option) {
                case 1 -> movieView.showMenu();
                case 2 -> hallView.showMenu();
                case 3 -> screenTimeView.showMenu();
                case 4 -> {
                    System.out.println("Exiting Main Menu.");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n== Main Menu ==");
        System.out.println("1. Movie Menu");
        System.out.println("2. Hall Menu");
        System.out.println("3. Screen Time Menu");
        System.out.println("4. Exit");
    }
}
