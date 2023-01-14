package org.meetingroom;

import java.time.LocalDateTime;

public interface ReservationService {
    /**
     * @return the {@link Reservation} if the meeting is reserved successfully or null if not
     */
    Reservation reserve(User host, MeetingRoom meetingRoom, LocalDateTime startTimestamp, LocalDateTime endTimestamp);

    boolean cancel(Reservation reservation);

    MeetingRoom findMeetingRoom(String roomId);

    User findUser(String userId);

    void printMeetingRoomSchedule(MeetingRoom meetingRoom, LocalDateTime startTimestamp, LocalDateTime endTimestamp);
}
