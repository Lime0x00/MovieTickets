package com.example.models;

import com.example.converters.InstantAttributeConverter;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "screen_time", schema = "MovieTickets1")
public class ScreenTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screen_time_id", nullable = false)
    private Integer id;

    @Convert(converter = InstantAttributeConverter.class)
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

    @Convert(converter = InstantAttributeConverter.class)
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @OneToMany(mappedBy = "screenTime")
    private Set<Receipt> receipts = new LinkedHashSet<>();

    @OneToMany(mappedBy = "screenTime")
    private Set<ScreenTimeSeat> screenTimeSeats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "screenTime")
    private Set<SeatHasReceipt> seatHasReceipts = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Set<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(Set<Receipt> receipts) {
        this.receipts = receipts;
    }

    public Set<com.example.models.ScreenTimeSeat> getScreenTimeSeats() {
        return screenTimeSeats;
    }

    public void setScreenTimeSeats(Set<com.example.models.ScreenTimeSeat> screenTimeSeats) {
        this.screenTimeSeats = screenTimeSeats;
    }

    public Set<com.example.models.SeatHasReceipt> getSeatHasReceipts() {
        return seatHasReceipts;
    }

    public void setSeatHasReceipts(Set<com.example.models.SeatHasReceipt> seatHasReceipts) {
        this.seatHasReceipts = seatHasReceipts;
    }

    
    @Override
    public String toString() {
        return "ScreenTime{" + "id=" + id + ", endDate=" + endDate + ", startDate=" + startDate + '}';
    }
}