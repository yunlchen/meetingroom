package org.meetingroom.utils;

import com.google.common.collect.Lists;
import org.meetingroom.impl.ReservationCollection;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
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

    public static List<ReservationCollection.TimeRange> merge(List<ReservationCollection.TimeRange> unmerged) {
        if (unmerged.isEmpty()) {
            return Lists.newArrayList();
        }
        List<ReservationCollection.TimeRange> merged = Lists.newArrayList();
        ReservationCollection.TimeRange currentTimeRange = new ReservationCollection.TimeRange(
            unmerged.get(0).getFromTimestamp(),
            unmerged.get(0).getToTimestamp()
        );
        for (int idx = 1; idx < unmerged.size(); ++idx) {
            ReservationCollection.TimeRange nextTimeRange = unmerged.get(idx);
            if (!continuous(currentTimeRange, nextTimeRange)) {
                merged.add(currentTimeRange);
                currentTimeRange = new ReservationCollection.TimeRange(
                    nextTimeRange.getFromTimestamp(),
                    nextTimeRange.getToTimestamp()
                );
            } else {
                extend(currentTimeRange, nextTimeRange);
            }
        }
        merged.add(currentTimeRange);
        return merged;
    }

    private static void extend(ReservationCollection.TimeRange currentTimeRange,
                               ReservationCollection.TimeRange nextTimeRange) {
        currentTimeRange.setToTimestamp(nextTimeRange.getToTimestamp());
    }

    private static boolean continuous(ReservationCollection.TimeRange currentTimeRange,
                                      ReservationCollection.TimeRange nextTimeRange) {
        return currentTimeRange.getToTimestamp().plusMinutes(1).isEqual(nextTimeRange.getFromTimestamp());
    }
}
