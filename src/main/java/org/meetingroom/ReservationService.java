package org.meetingroom;

import org.meetingroom.impl.ReservationCollection;

import java.time.LocalDateTime;
import java.util.List;

/**
 * A meeting room reservation service. The granularity is minute.
 */
public interface ReservationService {
    /**
     * The time range is [{@param startTimestamp}, {@param endTimestamp}]
     * @return the {@link Reservation} if the meeting is reserved successfully or null if not
     */
    Reservation reserve(User host,
                        MeetingRoom meetingRoom,
                        LocalDateTime startTimestamp,
                        LocalDateTime endTimestamp);

    /**
     * @return whether cancel the {@link Reservation} successfully or not
     */
    boolean cancel(Reservation reservation);

    /**
     * @return whether there is an existing {@link MeetingRoom}
     */
    boolean registerMeetingRoom(MeetingRoom meetingRoom);

    MeetingRoom findMeetingRoom(String roomId);

    /**
     * @return whether there is an existing {@link User}
     */
    boolean registerUser(User user);

    User findUser(String userId);

    List<ReservationCollection.TimeRange> getMeetingRoomSchedule(MeetingRoom meetingRoom,
                                                                 LocalDateTime fromTimestamp,
                                                                 LocalDateTime toTimestamp);
}
