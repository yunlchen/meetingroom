package org.meetingroom.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.meetingroom.MeetingRoom;
import org.meetingroom.Reservation;
import org.meetingroom.User;
import org.meetingroom.utils.IdUtils;
import org.meetingroom.utils.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationCollectionPerDay {
    private final MeetingRoom room;
    private final LocalDate day;
    private final SegmentTree segmentTree;

    public ReservationCollectionPerDay(MeetingRoom meetingRoom, LocalDate localDate) {
        room = meetingRoom;
        day = localDate;
        segmentTree = new SegmentTree(24 * 60);
    }

    public boolean checkAvailable(LocalDateTime startTimestamp, LocalDateTime endTimestamp) {
        Preconditions.checkArgument(TimeUtils.sameDay(day, startTimestamp));
        Preconditions.checkArgument(TimeUtils.sameDay(day, endTimestamp));
        return !segmentTree.query(TimeUtils.startOfDayMinutes(startTimestamp), TimeUtils.startOfDayMinutes(endTimestamp));
    }

    public Reservation doReserve(LocalDateTime startTimestamp, LocalDateTime endTimestamp, User host) {
        Preconditions.checkArgument(TimeUtils.sameDay(day, startTimestamp));
        Preconditions.checkArgument(TimeUtils.sameDay(day, endTimestamp));
        segmentTree.add(TimeUtils.startOfDayMinutes(startTimestamp), TimeUtils.startOfDayMinutes(endTimestamp));
        return new Reservation(
            IdUtils.nanoId(),
            room,
            host,
            Lists.newArrayList(),
            startTimestamp,
            endTimestamp
        );
    }

    public List<ReservationCollection.TimeRange> getReservedTimeRanges(LocalDateTime fromTimestamp, LocalDateTime toTimestamp) {
        Preconditions.checkArgument(TimeUtils.sameDay(day, fromTimestamp));
        Preconditions.checkArgument(TimeUtils.sameDay(day, toTimestamp));
        List<ReservationCollection.TimeRange> unmerged = segmentTree.getAllAdded().stream().map(
            segmentNode -> new ReservationCollection.TimeRange(
                TimeUtils.getStartOfDay(day).plusMinutes(segmentNode.left),
                TimeUtils.getStartOfDay(day).plusMinutes(segmentNode.right)
            )
        ).filter(timeRange -> timeRange.inRangeOf(fromTimestamp, toTimestamp)).toList();
        return TimeUtils.merge(unmerged);
    }
}
