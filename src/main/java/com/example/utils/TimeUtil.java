package com.example.utils;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    public static String duration(double durationInHours) {
        if (durationInHours <= 0) {
            return "Invalid duration";
        }

        if (durationInHours < 1) {
            long minutes = Math.round(durationInHours * 60);
            return minutes + (minutes == 1 ? " minute" : " minutes");
        }

        long hours = (long) durationInHours;
        long minutes = Math.round((durationInHours - hours) * 60);

        if (minutes == 0) {
            return hours + (hours == 1 ? " hour" : " hours");
        } else {
            return String.format("%d %s, %d %s",
                    hours, (hours == 1 ? "hour" : "hours"),
                    minutes, (minutes == 1 ? "minute" : "minutes"));
        }
    }

    public static String duration(Instant start, Instant end) {
        if (start == null || end == null) {
            return "Invalid dates";
        }

        if (end.isBefore(start)) {
            return "Invalid duration";
        }

        long durationInMillis = Duration.between(start, end).toMillis();
        double durationInHours = durationInMillis / (1000.0 * 60 * 60);

        return duration(durationInHours);
    }

    public static String date(Instant date, String pattern) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern)
                .withZone(ZoneId.systemDefault());
        return dateFormat.format(date);
    }
    
}
