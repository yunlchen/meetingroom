package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

public class S775 {
    public boolean isIdealPermutation(int[] nums) {
        if (nums.length == 1 || nums.length == 2) {
            return true;
        }
        int[] max = new int[nums.length];
        for (int idx = 0; idx < nums.length; ++idx) {
            if (idx == 0) {
                max[idx] = nums[idx];
                continue;
            } else {
                max[idx] = Math.max(max[idx - 1], nums[idx]);
            }

            if (idx == 1) {
                continue;
            }

            if (nums[idx] < max[idx - 2]) {
                return false;
            }
        }
        return true;
    }

    @Test
    void test() {
        System.out.println(isIdealPermutation(new int[]{1, 2, 0}));
    }
}
