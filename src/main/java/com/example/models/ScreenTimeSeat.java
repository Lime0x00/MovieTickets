package com.example.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "screen_time_seat", schema = "MovieTickets1")
public class ScreenTimeSeat {
    @EmbeddedId
    private ScreenTimeSeatId id;

    @MapsId("screenTimeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "screen_time_id", nullable = false)
    private ScreenTime screenTime;

    @MapsId("seatId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    private com.example.models.Seat seat;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable = false;

    public ScreenTimeSeatId getId() {
        return id;
    }

    public void setId(ScreenTimeSeatId id) {
        this.id = id;
    }

    public ScreenTime getScreenTime() {
        return screenTime;
    }

    public void setScreenTime(ScreenTime screenTime) {
        this.screenTime = screenTime;
    }

    public com.example.models.Seat getSeat() {
        return seat;
    }

    public void setSeat(com.example.models.Seat seat) {
        this.seat = seat;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    
}