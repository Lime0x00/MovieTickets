package com.example.models;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "hall", schema = "MovieTickets1")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "number_of_cols", nullable = false)
    private Integer numberOfCols;

    @Column(name = "number_of_rows", nullable = false)
    private Integer numberOfRows;

    @OneToMany(mappedBy = "hall")
    private Set<ScreenTime> screenTimes = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberOfCols() {
        return numberOfCols;
    }

    public void setNumberOfCols(Integer numberOfCols) {
        this.numberOfCols = numberOfCols;
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Set<ScreenTime> getScreenTimes() {
        return screenTimes;
    }

    public void setScreenTimes(Set<com.example.models.ScreenTime> screenTimes) {
        this.screenTimes = screenTimes;
    }

    
    @Override
    public String toString() {
        return "Hall{" + "id=" + id + ", numberOfCols=" + numberOfCols + ", numberOfRows=" + numberOfRows + '}';
    }
}