package org.meetingroom;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Reservation {
    private String id;
    private MeetingRoom room;
    private User host;
    private List<User> attenders;
    private LocalDateTime startTimestamp;
    private LocalDateTime endTimestamp;
}
