package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

public class S799 {
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[][] dp = new double[query_row + 10][query_row + 10];
        dp[0][0] = poured;
        for (int i = 0; i <= query_row; ++i) {
            for (int j = 0; j <= query_row; ++j) {
                if (dp[i][j] <= 1) {
                    continue;
                }
                double rest = dp[i][j] - 1;
                dp[i][j] = 1;
                dp[i + 1][j] += rest / 2;
                dp[i + 1][j + 1] += rest / 2;
            }
        }
        return dp[query_row][query_glass];
    }

    @Test
    void test() {
        System.out.println(champagneTower(1, 1, 1));
        System.out.println(champagneTower(2, 1, 1));
        System.out.println(champagneTower(100000009, 33, 17));
    }
}
