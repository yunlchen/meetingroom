package org.meetingroom.utils;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

public class IdUtils {
    private IdUtils() {
    }

    public static String nanoId() {
        return NanoIdUtils.randomNanoId();
    }
}
