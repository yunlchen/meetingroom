package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class S1817 {
    public int[] findingUsersActiveMinutes(int[][] logs, int k) {
        Map<Integer, Set<Integer>> count = new HashMap<>();
        Map<Integer, Integer> answer = new HashMap<>();
        for (int[] eachLog : logs) {
            int id = eachLog[0];
            int actionMin = eachLog[1];
            count.putIfAbsent(id, new HashSet<>());
            count.get(id).add(actionMin);
        }
        for (Map.Entry<Integer, Set<Integer>> each : count.entrySet()) {
            answer.put(each.getValue().size(), answer.getOrDefault(each.getValue().size(), 0) + 1);
        }
        int[] tmp = new int[k];
        for (int idx = 0; idx < k; ++idx) {
            tmp[idx] = answer.getOrDefault(idx + 1, 0);
        }
        return tmp;
    }

    @Test
    void test() {
        System.out.println(Arrays.toString(findingUsersActiveMinutes(new int[][]{
            new int[]{1, 1},
            new int[]{2, 2},
            new int[]{2, 3},
        }, 4)));
    }
}
