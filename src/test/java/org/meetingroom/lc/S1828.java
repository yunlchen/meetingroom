package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class S1828 {
    public int[] countPoints(int[][] points, int[][] queries) {
        TreeSet<int[]> orderedX = new TreeSet<>((a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            }
            return a[1] - b[1];
        });
        TreeSet<int[]> orderedY = new TreeSet<>((a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            }
            return a[1] - b[1];
        });
        for (int idx = 0; idx < points.length; ++idx) {
            orderedX.add(new int[]{points[idx][0], idx});
            orderedY.add(new int[]{points[idx][1], idx});
        }

        int[] answer = new int[queries.length];
        for (int idx = 0; idx < queries.length; ++idx) {
            int x = queries[idx][0];
            int y = queries[idx][1];
            int diff = queries[idx][2];

            Set<Integer> candidateIdxFromX = new HashSet<>();
            for (int[] each : orderedX.subSet(new int[]{x - diff, -1}, true, new int[]{x + diff, points.length}, true)) {
                candidateIdxFromX.add(each[1]);
            }

            Set<Integer> candidateIdxFromY = new HashSet<>();
            for (int[] each : orderedY.subSet(new int[]{y - diff, -1}, true, new int[]{y + diff, points.length}, true)) {
                candidateIdxFromY.add(each[1]);
            }

            Set<Integer> candidateIdx = candidateIdxFromX.stream().filter(candidateIdxFromY::contains).collect(Collectors.toSet());
            for (int each : candidateIdx) {
                double dist = dist(x, y, points[each][0], points[each][1]);
                if (diff > dist || Math.abs(diff - dist) < Math.pow(10, -8)) {
                    answer[idx]++;
                }
            }
        }
        return answer;
    }

    private double dist(int firstX, int firstY, int secondX, int secondY) {
        return Math.sqrt(Math.pow(firstX - secondX, 2) + Math.pow(firstY - secondY, 2));
    }

    @Test
    void test() {
        System.out.println(Arrays.toString(countPoints(
            new int[][]{
                {1, 3},
                {3, 3},
                {5, 3},
                {2, 2},
            },
            new int[][]{
                {2, 3, 1},
                {4, 3, 1},
                {1, 1, 2},
            }
        )));
    }
}
