package com.example.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "seat", schema = "MovieTickets1")
public class Seat {
    @Id
    @Column(name = "id", nullable = false, length = 10)
    private String id;

    @Lob
    @Column(name = "class", nullable = false)
    private String classField;

    @Column(name = "default_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal defaultPrice;

    @OneToMany(mappedBy = "seat")
    private Set<ScreenTimeSeat> screenTimeSeats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "seat")
    private Set<com.example.models.SeatHasReceipt> seatHasReceipts = new LinkedHashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassField() {
        return classField;
    }

    public void setClassField(String classField) {
        this.classField = classField;
    }

    public BigDecimal getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(BigDecimal defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Set<ScreenTimeSeat> getScreenTimeSeats() {
        return screenTimeSeats;
    }

    public void setScreenTimeSeats(Set<ScreenTimeSeat> screenTimeSeats) {
        this.screenTimeSeats = screenTimeSeats;
    }

    public Set<com.example.models.SeatHasReceipt> getSeatHasReceipts() {
        return seatHasReceipts;
    }

    public void setSeatHasReceipts(Set<com.example.models.SeatHasReceipt> seatHasReceipts) {
        this.seatHasReceipts = seatHasReceipts;
    }

}