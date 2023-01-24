package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class S1620 {
    public int[] bestCoordinate(int[][] towers, int radius) {
        int[][] tmp = new int[110 + 1][110 + 1];
        for (int[] tower : towers) {
            for (int x = Math.max(0, tower[0] - radius); x <= Math.min(110, tower[0] + radius); ++x) {
                for (int y = Math.max(0, tower[1] - radius); y <= Math.min(110, tower[1] + radius); ++y) {
                    double dist = Math.sqrt(Math.pow(x - tower[0], 2) + Math.pow(y - tower[1], 2));
                    if (dist <= radius) {
                        tmp[x][y] += (int) Math.floor((double) tower[2] / (1 + dist));
                    }
                }
            }
        }
        int maxX = -1;
        int maxY = -1;
        int max = Integer.MIN_VALUE;
        for (int x = 0; x < 110; ++x) {
            for (int y = 0; y < 110; ++y) {
                if (tmp[x][y] > max) {
                    max = tmp[x][y];
                    maxX = x;
                    maxY = y;
                } else if (tmp[x][y] == max && (x < maxX || (x == maxX && y < maxY))) {
                    maxX = x;
                    maxY = y;
                }
            }
        }
        return new int[]{maxX, maxY};
    }

    @Test
    void test() {
        System.out.println(Arrays.toString(bestCoordinate(new int[][]{
            {42, 0, 0}
        }, 7)));
    }
}
