package com.example.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.util.Objects;

@Embeddable
public class ScreenTimeSeatId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 8571935530105409105L;
    @Column(name = "screen_time_id", nullable = false)
    private Integer screenTimeId;

    @Column(name = "seat_id", nullable = false, length = 10)
    private String seatId;

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
        ScreenTimeSeatId entity = (ScreenTimeSeatId) o;
        return Objects.equals(this.seatId, entity.seatId) &&
                Objects.equals(this.screenTimeId, entity.screenTimeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatId, screenTimeId);
    }

}