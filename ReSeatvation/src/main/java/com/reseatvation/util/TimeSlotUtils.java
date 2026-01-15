package com.reseatvation.util;

import com.reseatvation.config.CinemaRules;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class TimeSlotUtils {
    private TimeSlotUtils() {
    }

    public static boolean isAlignedToSlot(LocalDateTime dt) {
        return dt.getSecond() == 0 && dt.getNano() == 0 && (dt.getMinute() % CinemaRules.SLOT_MINUTES == 0);
    }

    public static LocalDateTime ceilToSlot(LocalDateTime dt) {
        LocalDateTime clean = dt.withSecond(0).withNano(0);
        int mod = clean.getMinute() % CinemaRules.SLOT_MINUTES;
        if (mod == 0) return clean;
        return clean.plusMinutes(CinemaRules.SLOT_MINUTES - mod);
    }

    public static LocalDateTime openingOf(LocalDate date) {
        return ceilToSlot(LocalDateTime.of(date, CinemaRules.OPEN_TIME));
    }


    public static LocalDateTime closingOf(LocalDate date) {
        return LocalDateTime.of(date, CinemaRules.CLOSE_TIME);
    }

    public static boolean fitsWithinOpeningHours(LocalDateTime start, LocalDateTime end) {
        if (!end.toLocalDate().equals(start.toLocalDate())) return false;
        LocalDate date = start.toLocalDate();
        return !start.isBefore(openingOf(date)) && !end.isAfter(closingOf(date));
    }

}
