package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class S1819 {
    public int countDifferentSubsequenceGCDs(int[] nums) {
        int max = Arrays.stream(nums).max().getAsInt();
        boolean[] check = new boolean[max + 1];
        for (int num : nums) {
            check[num] = true;
        }
        int answer = 0;
        for (int x = 1; x < max + 1; ++x) {
            int gcd = -1;
            for (int nx = x; nx < max + 1; nx += x) {
                if (check[nx]) {
                    if (gcd == -1) {
                        gcd = nx;
                    } else {
                        gcd = gcd(gcd, nx);
                    }

                    if (gcd == x) {
                        ++answer;
                        break;
                    }
                }
            }
        }
        return answer;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    @Test
    void test() {
        System.out.println(countDifferentSubsequenceGCDs(new int[]{6, 10, 3}));
    }
}
