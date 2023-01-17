package org.meetingroom.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.meetingroom.MeetingRoom;
import org.meetingroom.Reservation;
import org.meetingroom.ReservationService;
import org.meetingroom.User;
import org.meetingroom.utils.TimeUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReservationServiceImpl implements ReservationService {
    private final Map<String, MeetingRoom> allMeetingRoom;
    private final Map<String, User> allUser;
    private final Map<MeetingRoom, ReservationCollection> allReservation;

    public ReservationServiceImpl() {
        allMeetingRoom = Maps.newHashMap();
        allUser = Maps.newHashMap();
        allReservation = Maps.newHashMap();
    }

    @Override
    public Reservation reserve(User host,
                               MeetingRoom meetingRoom,
                               LocalDateTime startTimestamp,
                               LocalDateTime endTimestamp) {
        startTimestamp = TimeUtils.downgradeToMinute(startTimestamp);
        endTimestamp = TimeUtils.downgradeToMinute(endTimestamp);
        allReservation.putIfAbsent(meetingRoom, new ReservationCollection(meetingRoom));
        return allReservation.get(meetingRoom).reserve(startTimestamp, endTimestamp, host);
    }

    @Override
    public boolean cancel(Reservation reservation) {
        if (!allReservation.containsKey(reservation.getRoom())) {
            return false;
        }
        return allReservation.get(reservation.getRoom()).remove(reservation);
    }

    @Override
    public boolean registerMeetingRoom(MeetingRoom meetingRoom) {
        return allMeetingRoom.putIfAbsent(meetingRoom.getRoomId(), meetingRoom) == null;
    }

    @Override
    public MeetingRoom findMeetingRoom(String roomId) {
        return Preconditions.checkNotNull(allMeetingRoom.get(roomId), "NO meeting room %s", roomId);
    }

    @Override
    public boolean registerUser(User user) {
        return allUser.putIfAbsent(user.getUserId(), user) == null;
    }

    @Override
    public User findUser(String userId) {
        return Preconditions.checkNotNull(allUser.get(userId), "NO user %s", userId);
    }

    @Override
    public List<ReservationCollection.TimeRange> getMeetingRoomSchedule(MeetingRoom meetingRoom,
                                                                        LocalDateTime fromTimestamp,
                                                                        LocalDateTime toTimestamp) {
        fromTimestamp = TimeUtils.downgradeToMinute(fromTimestamp);
        toTimestamp = TimeUtils.downgradeToMinute(toTimestamp);
        if (!allReservation.containsKey(meetingRoom)) {
            return Collections.emptyList();
        }
        return allReservation.get(meetingRoom).getSchedule(fromTimestamp, toTimestamp);
    }
}
