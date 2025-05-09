package com.example.utils;

import java.time.Instant;
import java.util.Date;

public class DateUtil {
    public static Instant toInstant(Date date) {
        if (date == null) {
            return null;
        }
        return date.toInstant();
    }

    public static Date toDate(Instant instant) {
        if (instant == null) {
            return null;
        }
        return java.util.Date.from(instant);
    }

    public static boolean datesOverlap(Date start1, Date end1, Date start2, Date end2) {
        return !(end1.before(start2) || start1.after(end2));
    }

    public static boolean isTodayOrWithinOneWeek(Date date) {
        Date now = new Date();

        Date oneWeekFromNow = new Date(now.getTime() + (7L * 24 * 60 * 60 * 1000));

        return !date.before(now) && !date.after(oneWeekFromNow);
    }
}
