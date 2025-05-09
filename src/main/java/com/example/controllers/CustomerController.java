package com.example.controllers;

import com.example.models.*;
import com.example.services.*;
import com.sun.jna.IntegerType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CustomerController  {
    private final CustomerService customerService;
    private final ReceiptService receiptService;
    private final ScreenTimeService screenTimeService;

    public CustomerController(CustomerService customerService, ReceiptService receiptService, ScreenTimeService screenTimeService) {
        this.customerService = customerService;
        this.receiptService = receiptService;
        this.screenTimeService = screenTimeService;
    }

    
    public List<Customer> getAllCustomers() {
        return customerService.findAll();
    }
    /**
     * Attempt to book the given seats for the customer & screenTime.
     * Returns the new Receipt on success, or null on failure.
     */
    public Receipt bookMovie(Customer customer, ScreenTime screenTime, Set<Seat> seats) {
        Integer customerId = customer.getId();
        Integer screenTimeId = screenTime.getId(); 
        
        if (customerId == null || screenTimeId == null || seats == null || seats.isEmpty()) {
            throw new IllegalArgumentException("Missing required fields");
        }

        Customer existCustomer = customerService.findById(customerId);
        if (existCustomer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        ScreenTime existScreenTime = screenTimeService.findById(screenTimeId);
        if (existScreenTime == null) {
            throw new IllegalArgumentException("ScreenTime not found");
        }

        Set<ScreenTimeSeat> screenTimeSeats = new HashSet<>();
        for (Seat seat : seats) {
            ScreenTimeSeat screenTimeSeat = new ScreenTimeSeat();
            screenTimeSeat.setSeat(seat);
            screenTimeSeat.setScreenTime(screenTime);
            screenTimeSeat.setIsAvailable(true); 
            screenTimeSeats.add(screenTimeSeat);
        }

        Receipt receipt = customerService.prepareReceipt(customer, screenTime, screenTimeSeats);

        receiptService.save(receipt);

        return receipt;
    }

}
