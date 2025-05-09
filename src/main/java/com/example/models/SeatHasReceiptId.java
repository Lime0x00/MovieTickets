package com.example.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class SeatHasReceiptId implements java.io.Serializable {
    private static final long serialVersionUID = 5271298170587828131L;
    @Column(name = "receipt_id", nullable = false)
    private Integer receiptId;

    @Column(name = "screen_time_id", nullable = false)
    private Integer screenTimeId;

    @Column(name = "seat_id", nullable = false, length = 10)
    private String seatId;

    public Integer getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Integer receiptId) {
        this.receiptId = receiptId;
    }

    public Integer getScreenTimeId() {
        return screenTimeId;
    }

    public void setScreenTimeId(Integer screenTimeId) {
        this.screenTimeId = screenTimeId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeatHasReceiptId entity = (SeatHasReceiptId) o;
        return Objects.equals(this.seatId, entity.seatId) &&
                Objects.equals(this.screenTimeId, entity.screenTimeId) &&
                Objects.equals(this.receiptId, entity.receiptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatId, screenTimeId, receiptId);
    }

}