package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class S1825 {
    static class MKAverage {
        int m;
        int k;
        PriorityQueue<Integer> lowQueue;
        PriorityQueue<Integer> highQueue;
        PriorityQueue<Integer> midQueue;
        PriorityQueue<Integer> reversedMidQueue;
        int midQueueSum;
        Queue<Integer> allNum;

        public MKAverage(int m, int k) {
            this.m = m;
            this.k = k;
            this.lowQueue = new PriorityQueue<>((a, b) -> b - a);
            this.highQueue = new PriorityQueue<>();
            this.midQueue = new PriorityQueue<>();
            this.reversedMidQueue = new PriorityQueue<>((a, b) -> b - a);
            this.allNum = new LinkedList<>();
            this.midQueueSum = 0;
        }

        public void addElement(int num) {
            allNum.add(num);

            if (lowQueue.size() < k) {
                lowQueue.add(num);
                return;
            }

            if (highQueue.size() < k) {
                if (lowQueue.peek() > num) {
                    shift(lowQueue, highQueue, num);
                } else {
                    highQueue.add(num);
                }

                return;
            }

            if (midQueue.size() < m - 2 * k) {
                if (num >= lowQueue.peek() && num <= highQueue.peek()) {
                    midQueue.add(num);
                    reversedMidQueue.add(num);
                    midQueueSum += num;
                } else if (num < lowQueue.peek()) {
                    int shifted = shift(lowQueue, midQueue, num);
                    reversedMidQueue.add(shifted);
                    midQueueSum += shifted;
                } else {
                    int shifted = shift(highQueue, midQueue, num);
                    reversedMidQueue.add(shifted);
                    midQueueSum += shifted;
                }
            } else {
                int removed = allNum.poll();
                if (removed <= lowQueue.peek()) {
                    lowQueue.remove(removed);
                    int shifted = midQueue.poll();
                    reversedMidQueue.remove(shifted);
                    lowQueue.add(shifted);
                    midQueueSum -= shifted;
                } else if (removed < highQueue.peek()) {
                    midQueue.remove(removed);
                    reversedMidQueue.remove(removed);
                    midQueueSum -= removed;
                } else {
                    highQueue.remove(removed);
                    int shifted = reversedMidQueue.poll();
                    midQueue.remove(shifted);
                    midQueueSum -= shifted;
                    highQueue.add(shifted);
                }

                if (num >= lowQueue.peek() && num <= highQueue.peek()) {
                    midQueue.add(num);
                    reversedMidQueue.add(num);
                    midQueueSum += num;
                } else if (num < lowQueue.peek()) {
                    int shifted = shift(lowQueue, midQueue, num);
                    reversedMidQueue.add(shifted);
                    midQueueSum += shifted;
                } else {
                    int shifted = shift(highQueue, midQueue, num);
                    reversedMidQueue.add(shifted);
                    midQueueSum += shifted;
                }
            }
        }

        private int shift(PriorityQueue<Integer> fromQueue, PriorityQueue<Integer> toQueue, int num) {
            int shifted = fromQueue.poll();
            fromQueue.add(num);
            toQueue.add(shifted);
            return shifted;
        }

        public int calculateMKAverage() {
            if (allNum.size() < m) {
                return -1;
            }
            return (int) Math.floor((double) midQueueSum / (m - 2 * k));
        }
    }

    @Test
    void test() {
        MKAverage mkAverage = new MKAverage(3, 1);
        mkAverage.addElement(3);
        mkAverage.addElement(1);
        System.out.println(mkAverage.calculateMKAverage());
        mkAverage.addElement(10);
        System.out.println(mkAverage.calculateMKAverage());
        mkAverage.addElement(5);
        mkAverage.addElement(5);
        mkAverage.addElement(5);
        System.out.println(mkAverage.calculateMKAverage());
    }
}
