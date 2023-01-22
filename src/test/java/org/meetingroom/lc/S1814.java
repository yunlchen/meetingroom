package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class S1814 {
    int mod = 1_000_000_000 + 7;

    public int countNicePairs(int[] nums) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int each : nums) {
            int key = each - rev(each);
            count.put(key, count.getOrDefault(key, 0) + 1);
        }
        long answer = 0L;
        for (Map.Entry<Integer, Integer> each : count.entrySet()) {
            answer = (answer + (long) each.getValue() * (each.getValue() - 1) / 2 % mod) % mod;
        }
        return (int) answer;
    }

    private int rev(int num) {
        int answer = 0;
        while (num > 0) {
            answer = answer * 10 + num % 10;
            num = num / 10;
        }
        return answer;
    }

    @Test
    void test() {
        System.out.println(countNicePairs(new int[]{13, 10, 35, 24, 76}));
    }
}
