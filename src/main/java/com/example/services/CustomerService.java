package com.example.services;

import com.example.models.*;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.util.*;

public class CustomerService extends BaseService<Customer> {

    public CustomerService() {
        super(Customer.class);
    }

    /**
     * Find all receipts (bookings) for a given customer
     */
    public List<Receipt> getReceiptsForCustomer(Integer customerId) {
        String jpql = "SELECT r FROM Receipt r WHERE r.customer.id = :customerId";
        TypedQuery<Receipt> query = em.createQuery(jpql, Receipt.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    /**
     * Check if customer has any bookings
     */
    public boolean hasBookings(Integer customerId) {
        String jpql = "SELECT COUNT(r) FROM Receipt r WHERE r.customer.id = :customerId";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("customerId", customerId)
                .getSingleResult();
        return count > 0;
    }

    /**
     * Book a movie: create a receipt for selected seats
     */
    public Receipt prepareReceipt(Customer customer, ScreenTime screenTime, Set<ScreenTimeSeat> screenTimeSeats) {
        Receipt receipt = new Receipt();
        receipt.setCustomer(customer);
        receipt.setScreenTime(screenTime);
        
        receipt.setTotalPrice(BigDecimal.valueOf(0.0));

        em.getTransaction().begin();
        em.persist(receipt);

        for (ScreenTimeSeat sts : screenTimeSeats) {
            SeatHasReceipt shr = new SeatHasReceipt();

            SeatHasReceiptId id = new SeatHasReceiptId();
            id.setReceiptId(receipt.getId());
            id.setScreenTimeId(screenTime.getId());
            id.setSeatId(sts.getSeat().getId());

            shr.setId(id);
            shr.setReceipt(receipt);
            shr.setScreenTime(screenTime);
            shr.setSeat(sts.getSeat());

            em.persist(shr);
            sts.setIsAvailable(false);
            em.merge(sts);
        }

        em.getTransaction().commit();
        return receipt;
    }

    /**
     * Cancel a booking (receipt)
     */
    public boolean cancelBooking(Integer receiptId) {
        Receipt receipt = em.find(Receipt.class, receiptId);
        if (receipt == null) {
            return false;
        }

        em.getTransaction().begin();

        // Remove SeatHasReceipt records
        for (SeatHasReceipt shr : receipt.getSeatHasReceipts()) {
            em.remove(em.contains(shr) ? shr : em.merge(shr));
        }

        em.remove(receipt);
        em.getTransaction().commit();
        return true;
    }

    /**
     * Find all movies a customer has booked
     */
    public List<Movie> getBookedMovies(Integer customerId) {
        String jpql = """
            SELECT DISTINCT r.screenTime.movie 
            FROM Receipt r 
            WHERE r.customer.id = :customerId
        """;
        TypedQuery<Movie> query = em.createQuery(jpql, Movie.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }
}
