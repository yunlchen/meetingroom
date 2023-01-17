package org.meetingroom.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.meetingroom.MeetingRoom;
import org.meetingroom.User;
import org.meetingroom.utils.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.mock;

public class ReservationCollectionPerDayTest {
    @Test
    void test() {
        LocalDateTime now = TimeUtils.getStartOfDay(LocalDate.now());
        LocalDateTime oneClock = now.plus(1, ChronoUnit.HOURS);
        LocalDateTime twoClock = now.plus(2, ChronoUnit.HOURS);
        LocalDateTime threeClock = now.plus(3, ChronoUnit.HOURS);
        LocalDateTime threeHalfClock = now.plus(3, ChronoUnit.HOURS).plus(30, ChronoUnit.MINUTES);
        LocalDateTime fourClock = now.plus(4, ChronoUnit.HOURS);
        ReservationCollectionPerDay reservationCollectionPerDay = new ReservationCollectionPerDay(
            mock(MeetingRoom.class),
            now.toLocalDate()
        );
        Assertions.assertTrue(reservationCollectionPerDay.checkAvailable(oneClock, threeClock));
        Assertions.assertTrue(reservationCollectionPerDay.getReservedTimeRanges(oneClock, threeClock).isEmpty());

        reservationCollectionPerDay.doReserve(oneClock, threeClock, mock(User.class));
        Assertions.assertFalse(reservationCollectionPerDay.checkAvailable(oneClock, threeClock));
        Assertions.assertFalse(reservationCollectionPerDay.checkAvailable(twoClock, threeClock));
        Assertions.assertFalse(reservationCollectionPerDay.checkAvailable(twoClock, fourClock));
        Assertions.assertTrue(reservationCollectionPerDay.checkAvailable(threeHalfClock, fourClock));

        Assertions.assertEquals(1, reservationCollectionPerDay.getReservedTimeRanges(now, fourClock).size());
        Assertions.assertEquals(oneClock, reservationCollectionPerDay.getReservedTimeRanges(now, fourClock).get(0).getFromTimestamp());
        Assertions.assertEquals(threeClock, reservationCollectionPerDay.getReservedTimeRanges(now, fourClock).get(0).getToTimestamp());
    }
}
