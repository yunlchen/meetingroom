package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class S1813 {
    public boolean areSentencesSimilar(String sentence1, String sentence2) {
        List<String> words1 = parse(sentence1);
        List<String> words2 = parse(sentence2);

        if (words1.size() < words2.size()) {
            List<String> tmp = words1;
            words1 = words2;
            words2 = tmp;
        }

        // words1 >= words2
        if (words1.size() == 1) {
            if (words1.get(0).equals(words2.get(0))) {
                return true;
            }
            return false;
        }

        if (words2.size() == 1) {
            if (words1.get(0).equals(words2.get(0)) || words1.get(words1.size() - 1).equals(words2.get(0))) {
                return true;
            }
            return false;
        }

        for (int rightStartIdx = 0; rightStartIdx <= words2.size(); ++rightStartIdx) {
            if (leftSame(words1, words2, 0, rightStartIdx - 1)
                && rightSame(words1, words2, rightStartIdx, words2.size() - 1)) {
                return true;
            }
        }

        return false;
    }

    private boolean rightSame(List<String> words1, List<String> words2, int rightStartIdxOfWords2, int rightEndIdxOfWords2) {
        if (rightStartIdxOfWords2 > rightEndIdxOfWords2) {
            return true;
        }
        int step = 0;
        for (int idxOfWords2 = rightStartIdxOfWords2; idxOfWords2 <= rightEndIdxOfWords2; ++idxOfWords2) {
            String word1 = words1.get(words1.size() - 1 - (rightEndIdxOfWords2 - rightStartIdxOfWords2) + step);
            step++;
            if (!words2.get(idxOfWords2).equals(word1)) {
                return false;
            }
        }
        return true;
    }

    private boolean leftSame(List<String> words1, List<String> words2, int fromIdx, int toIdx) {
        if (fromIdx > toIdx) {
            return true;
        }
        for (int idx = fromIdx; idx <= toIdx; ++idx) {
            if (!words1.get(idx).equals(words2.get(idx))) {
                return false;
            }
        }
        return true;
    }

    private List<String> parse(String sentence) {
        List<String> answer = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        for (int idx = 0; idx < sentence.length(); ++idx) {
            if (sentence.charAt(idx) == ' ') {
                answer.add(word.toString());
                word.setLength(0);
            } else {
                word.append(sentence.charAt(idx));
            }
        }
        if (word.length() > 0) {
            answer.add(word.toString());
        }
        return answer;
    }

    @Test
    void test() {
        System.out.println(areSentencesSimilar("My name is Haley", "My Haley"));
        System.out.println(areSentencesSimilar("of", "A lot of words"));
        System.out.println(areSentencesSimilar("Eating right now", "Eating"));
        System.out.println(areSentencesSimilar("Luky", "Lucccky"));
    }
}
