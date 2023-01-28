package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

public class S792 {
    public int numMatchingSubseq(String s, String[] words) {
        Queue<String>[] wordByFirstCharacter = new LinkedList[26];
        for (int idx = 0; idx < 26; ++idx) {
            wordByFirstCharacter[idx] = new LinkedList<>();
        }
        for (String word : words) {
            wordByFirstCharacter[word.charAt(0) - 'a'].add(word);
        }
        int answer = 0;
        for (int idx = 0; idx < s.length(); ++idx) {
            Queue<String> newQueue = new LinkedList<>();
            Queue<String> existingQueue = wordByFirstCharacter[s.charAt(idx) - 'a'];
            while (!existingQueue.isEmpty()) {
                String data = existingQueue.poll();
                if (data.length() == 1) {
                    answer++;
                } else {
                    String newData = data.substring(1);
                    if (newData.charAt(0) == s.charAt(idx)) {
                        newQueue.add(newData);
                    } else {
                        wordByFirstCharacter[newData.charAt(0) - 'a'].add(newData);
                    }
                }
            }
            existingQueue.addAll(newQueue);
        }
        return answer;
    }

    @Test
    void test() {
        System.out.println(numMatchingSubseq("dsahjpjauf", new String[]{
            "ahjpjau",
            "ja",
            "ahbwzgqnuk",
            "tnmlanowax"
        }));
    }
}
