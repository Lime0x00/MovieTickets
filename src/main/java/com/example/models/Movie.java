package com.example.models;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "movie", schema = "MovieTickets1")
public class Movie {

    public enum Genre {
        ACTION,
        ROMANTIC,
        COMEDY,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "duration", nullable = false)
    private Float duration;

    @Enumerated(EnumType.STRING)  
    @Column(name = "genre", nullable = false)
    private Genre genre;

    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @OneToMany(mappedBy = "movie")
    private Set<com.example.models.ScreenTime> screenTimes = new LinkedHashSet<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<com.example.models.ScreenTime> getScreenTimes() {
        return screenTimes;
    }

    public void setScreenTimes(Set<com.example.models.ScreenTime> screenTimes) {
        this.screenTimes = screenTimes;
    }
    
    @Override
    public String toString() {
        return "Movie{" + "id=" + id + ", duration=" + duration + ", genre=" + genre + ", title=" + title + '}';
    }

}
