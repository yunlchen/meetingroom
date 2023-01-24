package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class S816 {
    public List<String> ambiguousCoordinates(String s) {
        s = s.substring(1, s.length() - 1);
        List<String> answer = new ArrayList<>();
        for (int rightStartIdx = 1; rightStartIdx < s.length(); ++rightStartIdx) {
            List<String> leftPossible = possible(s.substring(0, rightStartIdx));
            List<String> rightPossible = possible(s.substring(rightStartIdx));
            for (String left : leftPossible) {
                for (String right : rightPossible) {
                    answer.add("(" + left + ", " + right + ")");
                }
            }
        }
        return answer;
    }

    private List<String> possible(String data) {
        List<String> answer = new ArrayList<>();
        if (!data.startsWith("0") || "0".equals(data)) {
            answer.add(data);
        }
        for (int dotIdx = 1; dotIdx < data.length(); ++dotIdx) {
            String tmp = data.substring(0, dotIdx) + "." + data.substring(dotIdx);
            if (valid(tmp)) {
                answer.add(tmp);
            }
        }
        return answer;
    }

    private boolean valid(String data) {
        if (data.startsWith("0") && data.charAt(1) != '.') {
            return false;
        }

        if (data.endsWith("0")) {
            return false;
        }

        return true;
    }

    @Test
    void test() {
        System.out.println(ambiguousCoordinates("(100)"));
    }
}
