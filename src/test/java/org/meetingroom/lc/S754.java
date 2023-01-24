package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

public class S754 {
    public int reachNumber(int target) {
        int result = 0;
        int num = 0;
        target = Math.abs(target);
        while (num < target || (num - target) % 2 != 0) {
            num += ++result;
        }
        return result;
    }

    @Test
    void test() {
        System.out.println(reachNumber(3));
    }
}
