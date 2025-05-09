package com.example.models;

import jakarta.persistence.*;

@Entity
@Table(name = "seat_has_receipt", schema = "MovieTickets1")
public class SeatHasReceipt {
    @EmbeddedId
    private SeatHasReceiptId id;

    @MapsId("receiptId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @MapsId("screenTimeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "screen_time_id", nullable = false)
    private ScreenTime screenTime;

    @MapsId("seatId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    public SeatHasReceiptId getId() {
        return id;
    }

    public void setId(SeatHasReceiptId id) {
        this.id = id;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public ScreenTime getScreenTime() {
        return screenTime;
    }

    public void setScreenTime(ScreenTime screenTime) {
        this.screenTime = screenTime;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

}