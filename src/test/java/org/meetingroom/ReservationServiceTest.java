package org.meetingroom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.meetingroom.impl.ReservationServiceImpl;
import org.meetingroom.utils.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ReservationServiceTest {
    User user = new User("yunlu");
    MeetingRoom meetingRoom = new MeetingRoom("beautiful_mind");

    @Test
    void test() {
        ReservationService reservationService = new ReservationServiceImpl();

        reservationService.registerUser(user);
        reservationService.registerMeetingRoom(meetingRoom);

        Assertions.assertEquals(user, reservationService.findUser("yunlu"));
        Assertions.assertEquals(meetingRoom, reservationService.findMeetingRoom("beautiful_mind"));

        LocalDateTime now = TimeUtils.getStartOfDay(LocalDate.now());
        LocalDateTime oneClock = now.plus(1, ChronoUnit.HOURS);
        LocalDateTime twoClock = now.plus(2, ChronoUnit.HOURS);
        LocalDateTime threeClock = now.plus(3, ChronoUnit.HOURS);
        LocalDateTime threeHalfClock = now.plus(3, ChronoUnit.HOURS).plus(30, ChronoUnit.MINUTES);
        LocalDateTime fourClock = now.plus(4, ChronoUnit.HOURS);
        LocalDateTime sixClock = now.plus(6, ChronoUnit.HOURS);

        reservationService.reserve(user, meetingRoom, twoClock, threeHalfClock);
        Assertions.assertFalse(reservationService.getMeetingRoomSchedule(meetingRoom, now, sixClock).isEmpty());
    }
}
