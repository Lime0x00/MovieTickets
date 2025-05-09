package com.example.services;

import java.util.List;
import com.example.models.Hall;
import com.example.models.ScreenTime;
import com.example.models.Seat;
import com.example.models.ScreenTimeSeat;


public class HallService extends BaseService<Hall> {

    public HallService() {
        super(Hall.class);
    }

    public boolean hasRelatedData(Hall hall) {
        List<ScreenTime> screenTimes = findRelatedScreenTimes(hall);
        List<Seat> seats = findRelatedSeats(hall);

        return !screenTimes.isEmpty() || !seats.isEmpty();
    }

    /**
     * Deletes a hall, ensuring related data is handled first.
     */
    public boolean deleteHall(Hall hall) {
        if (hasRelatedData(hall)) {
            return false;
        }
        return delete(hall);
    }

    /**
     * Retrieves the list of ScreenTime entities related to the hall.
     */
    private List<ScreenTime> findRelatedScreenTimes(Hall hall) {
        String jpql = "SELECT s FROM ScreenTime s WHERE s.hall.id = :hallId";
        return em.createQuery(jpql, ScreenTime.class)
                .setParameter("hallId", hall.getId())
                .getResultList();
    }

    /**
     * Retrieves the list of Seat entities related to the hall via ScreenTimeSeat.
     */
    private List<Seat> findRelatedSeats(Hall hall) {
        String jpql = "SELECT DISTINCT st.seat FROM ScreenTimeSeat st WHERE st.screenTime.hall.id = :hallId";
        return em.createQuery(jpql, Seat.class)
                .setParameter("hallId", hall.getId())
                .getResultList();
    }
}
