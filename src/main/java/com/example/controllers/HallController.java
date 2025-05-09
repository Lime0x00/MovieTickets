package com.example.controllers;

import java.util.List;

import com.example.models.Hall;
import com.example.models.ScreenTime;
import com.example.models.Seat;
import com.example.services.HallService;
import com.example.services.SeatService;
import com.example.services.ScreenTimeService;

public class HallController {
    private final HallService hallService;
    private final SeatService seatService;
    private final ScreenTimeService screenTimeService;

    public HallController(HallService hallService, SeatService seatService, ScreenTimeService screenTimeService) {
        this.hallService = hallService;
        this.seatService = seatService;
        this.screenTimeService = screenTimeService;
    }

    public boolean addHall(Hall hall) {
        return hallService.save(hall);
    }

    public List<Hall> getAllHalls() {
        return hallService.findAll();
    }

    public Hall getHallById(int id) {
        return hallService.findById(id);
    }

    public boolean deleteHall(Hall hall) {
        return hallService.deleteHall(hall);
    }

    public List<ScreenTime> getScreenTimesForHall(Hall hall) {
        return screenTimeService.findByHall(hall);  
    }

    public List<Seat> getSeatsForHall(Hall hall) {
        return seatService.findByHall(hall); 
    }
}
