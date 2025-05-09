package com.example.services;

import com.example.models.*;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class SeatService extends BaseService<Seat> {

    public SeatService() {
        super(Seat.class);
    }

    /**
     * Find all available seats for a given screen time
     */
    public List<Seat> findInAvailableByScreenTime(Integer screenTimeId) {
        String jpql = """
        SELECT sts.seat FROM ScreenTimeSeat sts 
        WHERE sts.screenTime.id = :screenTimeId AND sts.isAvailable = false
    """;
        return em.createQuery(jpql, Seat.class)
                .setParameter("screenTimeId", screenTimeId)
                .getResultList();
    }



    /**
     * Count available seats for a given screen time
     */
    public long countAvailableByScreenTime(Integer screenTimeId) {
        String jpql = """
            SELECT COUNT(sts) FROM ScreenTimeSeat sts 
            WHERE sts.screenTime.id = :screenTimeId AND sts.isAvailable = true
        """;
        return em.createQuery(jpql, Long.class)
                .setParameter("screenTimeId", screenTimeId)
                .getSingleResult();
    }

    /**
     * Mark a seat as available or unavailable for a screen time
     */
    public boolean markAvailable(Integer screenTimeId, String seatId, Boolean available) {
        if (screenTimeId == null || seatId == null || available == null) return false;

        try {
            runInTransaction(em -> {
                ScreenTimeSeatId stsId = new ScreenTimeSeatId();
                stsId.setScreenTimeId(screenTimeId);
                stsId.setSeatId(seatId);

                ScreenTimeSeat sts = em.find(ScreenTimeSeat.class, stsId);
                if (sts != null) {
                    sts.setIsAvailable(available);
                    em.merge(sts);
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all receipts for a specific seat in a screen time
     */
    public List<Receipt> getReceiptsForSeat(Integer screenTimeId, String seatId) {
        String jpql = """
            SELECT shr.receipt FROM SeatHasReceipt shr
            WHERE shr.id.screenTimeId = :screenTimeId AND shr.id.seatId = :seatId
        """;
        TypedQuery<Receipt> query = em.createQuery(jpql, Receipt.class);
        query.setParameter("screenTimeId", screenTimeId);
        query.setParameter("seatId", seatId);
        return query.getResultList();
    }

    /**
     * Find all seats associated with a specific hall.
     * We assume that a hall has many screen times, and each screen time has many seats.
     */
    public List<Seat> findByHall(Hall hall) {
        String jpql = """
            SELECT DISTINCT sts.seat FROM ScreenTimeSeat sts
            JOIN sts.screenTime st
            WHERE st.hall.id = :hallId
        """;
        TypedQuery<Seat> query = em.createQuery(jpql, Seat.class);
        query.setParameter("hallId", hall.getId());
        return query.getResultList();
    }
}
