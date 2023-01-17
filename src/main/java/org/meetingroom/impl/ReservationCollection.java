package org.meetingroom.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.meetingroom.MeetingRoom;
import org.meetingroom.Reservation;
import org.meetingroom.User;
import org.meetingroom.utils.IdUtils;
import org.meetingroom.utils.TimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReservationCollection {
    private final Map<String, Reservation> allReservation;
    private final Map<LocalDate, ReservationCollectionPerDay> data;
    private final MeetingRoom room;

    public ReservationCollection(MeetingRoom meetingRoom) {
        room = meetingRoom;
        allReservation = Maps.newHashMap();
        data = Maps.newHashMap();
    }

    public Reservation reserve(LocalDateTime startTimestamp,
                               LocalDateTime endTimestamp,
                               User host) {
        if (TimeUtils.sameDay(startTimestamp, endTimestamp)) {
            LocalDate localDate = startTimestamp.toLocalDate();
            data.putIfAbsent(localDate, new ReservationCollectionPerDay(room, localDate));
            boolean pass = data.get(localDate).checkAvailable(startTimestamp, endTimestamp);
            if (!pass) {
                return null;
            }

            return doReserve(startTimestamp, endTimestamp, host);
        }

        for (LocalDate tmp = startTimestamp.toLocalDate();
             tmp.isBefore(endTimestamp.toLocalDate()) || tmp.isEqual(endTimestamp.toLocalDate());
             tmp = tmp.plusDays(1)) {
            data.putIfAbsent(tmp, new ReservationCollectionPerDay(room, tmp));
            boolean pass;
            if (TimeUtils.sameDay(tmp, startTimestamp)) {
                pass = data.get(tmp).checkAvailable(
                    startTimestamp,
                    TimeUtils.getEndOfDay(tmp)
                );
            } else if (TimeUtils.sameDay(tmp, endTimestamp)) {
                pass = data.get(tmp).checkAvailable(
                    TimeUtils.getStartOfDay(tmp),
                    endTimestamp
                );
            } else {
                pass = data.get(tmp).checkAvailable(
                    TimeUtils.getStartOfDay(tmp),
                    TimeUtils.getEndOfDay(tmp)
                );
            }
            if (!pass) {
                return null;
            }
        }

        Reservation reservation = doReserve(startTimestamp, endTimestamp, host);
        allReservation.put(reservation.getId(), reservation);
        return reservation;
    }

    private Reservation doReserve(LocalDateTime startTimestamp, LocalDateTime endTimestamp, User host) {
        if (TimeUtils.sameDay(startTimestamp, endTimestamp)) {
            LocalDate localDate = startTimestamp.toLocalDate();
            return data.get(localDate).doReserve(startTimestamp, endTimestamp, host);
        }

        for (LocalDate tmp = startTimestamp.toLocalDate();
             tmp.isBefore(endTimestamp.toLocalDate()) || tmp.isEqual(endTimestamp.toLocalDate());
             tmp = tmp.plusDays(1)) {
            if (TimeUtils.sameDay(tmp, startTimestamp)) {
                data.get(tmp).doReserve(
                    startTimestamp,
                    TimeUtils.getEndOfDay(tmp),
                    host
                );
            } else if (TimeUtils.sameDay(tmp, endTimestamp)) {
                data.get(tmp).doReserve(
                    TimeUtils.getStartOfDay(tmp),
                    endTimestamp,
                    host
                );
            } else {
                data.get(tmp).doReserve(
                    TimeUtils.getStartOfDay(tmp),
                    TimeUtils.getEndOfDay(tmp),
                    host
                );
            }
        }
        return new Reservation(
            IdUtils.nanoId(),
            room,
            host,
            Lists.newArrayList(),
            startTimestamp,
            endTimestamp
        );
    }

    public boolean remove(Reservation reservation) {
        if (!allReservation.containsKey(reservation.getId())) {
            return false;
        }
        //TODO - yunlu
        return true;
    }

    public List<TimeRange> getSchedule(LocalDateTime fromTimestamp, LocalDateTime toTimestamp) {
        if (TimeUtils.sameDay(fromTimestamp, toTimestamp)) {
            LocalDate localDate = fromTimestamp.toLocalDate();
            if (!data.containsKey(localDate)) {
                return Collections.emptyList();
            }
            return data.get(localDate).getReservedTimeRanges(fromTimestamp, toTimestamp);
        }

        List<TimeRange> answer = Lists.newArrayList();
        for (LocalDate tmp = fromTimestamp.toLocalDate();
             tmp.isBefore(toTimestamp.toLocalDate()) || tmp.isEqual(toTimestamp.toLocalDate());
             tmp = tmp.plusDays(1)) {
            if (TimeUtils.sameDay(tmp, fromTimestamp)) {
                answer.addAll(
                    data.get(tmp).getReservedTimeRanges(
                        fromTimestamp,
                        TimeUtils.getEndOfDay(tmp)
                    )
                );
            } else if (TimeUtils.sameDay(tmp, toTimestamp)) {
                answer.addAll(
                    data.get(tmp).getReservedTimeRanges(
                        TimeUtils.getStartOfDay(tmp),
                        toTimestamp
                    )
                );
            } else {
                answer.addAll(
                    data.get(tmp).getReservedTimeRanges(
                        TimeUtils.getStartOfDay(tmp),
                        TimeUtils.getEndOfDay(tmp)
                    )
                );
            }
        }
        return TimeUtils.merge(answer);
    }

    @Data
    @AllArgsConstructor
    @ToString
    public static class TimeRange {
        private LocalDateTime fromTimestamp;
        private LocalDateTime toTimestamp;

        public boolean inRangeOf(LocalDateTime fromTimestamp, LocalDateTime toTimestamp) {
            if (this.fromTimestamp.isBefore(fromTimestamp)) {
                return false;
            }
            if (this.toTimestamp.isAfter(toTimestamp)) {
                return false;
            }
            return true;
        }
    }
}
