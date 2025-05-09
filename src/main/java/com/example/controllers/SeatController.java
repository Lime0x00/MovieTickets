package com.example.controllers;

import com.example.models.Seat;
import com.example.models.ScreenTime;
import com.example.services.SeatService;

import java.util.List;

public class SeatController {
    private SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // Adds a seat to the system
    public boolean addSeat(Seat seat) {
        return seatService.save(seat);
    }

    // Retrieves a seat by its ID
    public Seat getSeatById(int id) {
        return seatService.findById(id);  // Assuming this method is implemented in SeatService
    }

    // Deletes a seat from the system
    public boolean deleteSeat(Seat seat) {
        return seatService.delete(seat);  // Assuming this method is implemented in SeatService
    }

    // Updates seat information (e.g., changing seat status or other properties)
    public boolean updateSeat(Seat seat) {
        return seatService.update(seat);  // Assuming this method is implemented in SeatService
    }

    public List<Seat> getInAvailableSeatsForScreenTime(ScreenTime screenTime) {
        return seatService.findInAvailableByScreenTime(screenTime.getId());  
    }
}
