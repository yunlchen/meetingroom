package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class S891 {
    static int mod = 1_000_000_000 + 7;
    static int[] pow = new int[100_000 + 1];

    static {
        pow[0] = 1;
        for (int idx = 1; idx <= 100_000; ++idx) {
            pow[idx] = pow[idx - 1] * 2 % mod;
        }
    }

    public int sumSubseqWidths(int[] nums) {
        Arrays.sort(nums);
        long answer = 0L;
        for (int idx = 0; idx < nums.length; ++idx) {
            answer += (long) (pow[idx] - pow[nums.length - 1 - idx]) * nums[idx] % mod;
        }
        return (int) ((answer % mod + mod) % mod);
    }

    @Test
    void test() {
        System.out.println(sumSubseqWidths(new int[]{
            5, 69, 89, 92, 31, 16, 25, 45, 63, 40, 16, 56, 24, 40, 75, 82, 40, 12, 50, 62, 92, 44, 67, 38, 92, 22, 91, 24, 26, 21, 100, 42, 23, 56, 64, 43, 95, 76, 84, 79, 89, 4, 16, 94, 16, 77, 92, 9, 30, 13
        }));
    }
}
