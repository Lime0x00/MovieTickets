package com.example.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "receipt", schema = "MovieTickets1")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "screen_time_id", nullable = false)
    private com.example.models.ScreenTime screenTime;

    @OneToMany(mappedBy = "receipt")
    private Set<SeatHasReceipt> seatHasReceipts = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ScreenTime getScreenTime() {
        return screenTime;
    }

    public void setScreenTime(ScreenTime screenTime) {
        this.screenTime = screenTime;
    }

    public Set<SeatHasReceipt> getSeatHasReceipts() {
        return seatHasReceipts;
    }

    public void setSeatHasReceipts(Set<SeatHasReceipt> seatHasReceipts) {
        this.seatHasReceipts = seatHasReceipts;
    }
    
    @Override
    public String toString() {
        return "Receipt{" + "id=" + id + ", totalPrice=" + totalPrice + ", customer=" + customer + ", screenTime=" + screenTime + '}';
    }

}