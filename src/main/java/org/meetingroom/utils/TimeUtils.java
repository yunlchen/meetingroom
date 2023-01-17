package org.meetingroom.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class TimeUtils {
    private TimeUtils() {
    }

    public static LocalDateTime getStartOfDay(LocalDate localDate) {
        return localDate.atStartOfDay();
    }

    public static LocalDateTime getEndOfDay(LocalDate localDate) {
        return localDate.atStartOfDay()
            .plusDays(1)
            .toLocalDate()
            .atStartOfDay()
            .minus(1, ChronoUnit.MINUTES);
    }

    public static LocalDateTime downgradeToMinute(LocalDateTime localDateTime) {
        return localDateTime.truncatedTo(ChronoUnit.MINUTES);
    }

    public static int startOfDayMinutes(LocalDateTime localDateTime) {
        Duration duration = Duration.between(getStartOfDay(localDateTime.toLocalDate()), localDateTime);
        return (int) duration.toMinutes();
    }

    public static boolean sameDay(LocalDateTime first, LocalDateTime second) {
        return sameDay(first.toLocalDate(), second);
    }

    public static boolean sameDay(LocalDate first, LocalDateTime second) {
        return Objects.equals(first, second.toLocalDate());
    }
}
