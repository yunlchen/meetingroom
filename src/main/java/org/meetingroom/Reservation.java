package org.meetingroom;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Reservation {
    private MeetingRoom room;
    private User host;
    private List<User> attenders;
    private LocalDateTime startTimestamp;
    private LocalDateTime endTimestamp;
}
