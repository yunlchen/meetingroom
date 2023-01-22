package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class S1824 {
    int dp[][];
    int[] obstacles;
    int n;

    public int minSideJumps(int[] obstacles) {
        this.obstacles = obstacles;
        this.n = obstacles.length - 1;
        this.dp = new int[3][this.n + 2];
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < this.n + 2; ++y) {
                if (y != this.n + 1) {
                    this.dp[x][y] = -1;
                } else {
                    this.dp[x][y] = 0;
                }
            }
        }

        return dp(1, 1);
    }

    private int dp(int currentY, int nextX) {
        if (dp[currentY][nextX] >= 0) {
            return dp[currentY][nextX];
        }
        int anwser = Integer.MAX_VALUE;
        Set<Integer> available = new HashSet<>();
        available.add(0);
        available.add(1);
        available.add(2);
        if (obstacles[nextX - 1] > 0) {
            available.remove(obstacles[nextX - 1] - 1);
        }
        if (obstacles[nextX] > 0) {
            available.remove(obstacles[nextX] - 1);
        }
        for (int each : available) {
            if (each == currentY) {
                anwser = Math.min(anwser, dp(each, nextX + 1));
            } else {
                anwser = Math.min(anwser, 1 + dp(each, nextX + 1));
            }
        }
        dp[currentY][nextX] = anwser;
        return anwser;
    }

    @Test
    void test() {
        System.out.println(minSideJumps(new int[]{0,2,1,0,3,0}));
    }
}
