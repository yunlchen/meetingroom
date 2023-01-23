package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class S1769 {
    public int[] minOperations(String boxes) {
        int length = boxes.length();
        int[] tmp = new int[length];

        for (int idx = 0; idx < length; ++idx) {
            if (boxes.charAt(idx) == '1') {
                for (int diff = 1; idx - diff >= 0 || idx + diff < length; ++diff) {
                    if (idx - diff >= 0) {
                        tmp[idx - diff] += diff;
                    }
                    if (idx + diff < length) {
                        tmp[idx + diff] += diff;
                    }
                }
            }
        }
        return tmp;
    }

    @Test
    void test() {
        System.out.println(Arrays.toString(minOperations("001011")));
    }
}
