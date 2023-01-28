package org.meetingroom.lc;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class S1632 {
    static class Node {
        int idx;
        int value;
        Node father;
        Set<Node> in;
        Set<Node> out;

        Node(int value, int idx) {
            this.value = value;
            this.idx = idx;
            this.father = this;
            this.in = new HashSet<>();
            this.out = new HashSet<>();
        }
    }

    int width;
    int height;

    public int[][] matrixRankTransform(int[][] matrix) {
        width = matrix[0].length;
        height = matrix.length;
        Node[] nodes = new Node[width * height];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                nodes[y * width + x] = new Node(matrix[y][x], y * width + x);
            }
        }
        for (int y = 0; y < height; ++y) {
            Map<Integer, Node> sameRow = new HashMap<>();
            for (int x = 0; x < width; ++x) {
                if (!sameRow.containsKey(matrix[y][x])) {
                    sameRow.put(matrix[y][x], nodes[y * width + x]);
                } else {
                    connect(findFather(nodes[y * width + x]), findFather(sameRow.get(matrix[y][x])));
                }
            }
        }
        for (int x = 0; x < width; ++x) {
            Map<Integer, Node> sameColumn = new HashMap<>();
            for (int y = 0; y < height; ++y) {
                if (!sameColumn.containsKey(matrix[y][x])) {
                    sameColumn.put(matrix[y][x], nodes[y * width + x]);
                } else {
                    connect(findFather(nodes[y * width + x]), findFather(sameColumn.get(matrix[y][x])));
                }
            }
        }
        for (int y = 0; y < height; ++y) {
            TreeMap<Integer, Node> orderedSameRow = new TreeMap<>();
            for (int x = 0; x < width; ++x) {
                orderedSameRow.putIfAbsent(matrix[y][x], findFather(nodes[y * width + x]));
            }
            Node prev = null;
            for (Map.Entry<Integer, Node> entry : orderedSameRow.entrySet()) {
                if (prev == null) {
                    prev = entry.getValue();
                    continue;
                }
                order(prev, entry.getValue());
                prev = entry.getValue();
            }
        }
        for (int x = 0; x < width; ++x) {
            TreeMap<Integer, Node> orderedSameColumn = new TreeMap<>();
            for (int y = 0; y < height; ++y) {
                orderedSameColumn.putIfAbsent(matrix[y][x], findFather(nodes[y * width + x]));
            }
            Node prev = null;
            for (Map.Entry<Integer, Node> entry : orderedSameColumn.entrySet()) {
                if (prev == null) {
                    prev = entry.getValue();
                    continue;
                }
                order(prev, entry.getValue());
                prev = entry.getValue();
            }
        }
        int[][] answer = new int[height][width];
        Set<Node> startNode = new HashSet<>();
        for (int idx = 0; idx < height * width; ++idx) {
            Node node = findFather(nodes[idx]);
            if (node.in.isEmpty()) {
                assignZhi(node, 1, answer);
                startNode.add(node);
            }
        }
        topology(startNode, 1, answer);
        for (int idx = 0; idx < height * width; ++idx) {
            if (answer[idx / width][idx % width] == 0) {
                int fatherIdx = findFather(nodes[idx]).idx;
                assignZhi(nodes[idx], answer[fatherIdx / width][fatherIdx % width], answer);
            }
        }
        return answer;
    }

    private void assignZhi(Node node, int zhi, int[][] answer) {
        answer[node.idx / width][node.idx % width] = zhi;
    }

    private void topology(Set<Node> nodeSet, int zhi, int[][] answer) {
        if (nodeSet.isEmpty()) {
            return;
        }
        Set<Node> nextLevelCandidate = new HashSet<>();
        for (Node node : nodeSet) {
            Set<Node> tmp = new HashSet<>(node.out);
            for (Node each : tmp) {
                disOrder(node, each);
                nextLevelCandidate.add(each);
            }
        }
        Set<Node> nextLevel = nextLevelCandidate.stream().filter(node -> node.in.isEmpty()).collect(Collectors.toSet());
        nextLevel.forEach(node -> assignZhi(node, zhi + 1, answer));
        topology(nextLevel, zhi + 1, answer);
    }

    private void disOrder(Node prev, Node curr) {
        prev.out.remove(curr);
        curr.in.remove(prev);
    }

    private void order(Node prev, Node curr) {
        prev.out.add(curr);
        curr.in.add(prev);
    }

    private void connect(Node child, Node father) {
        child.father = father;
    }

    private Node findFather(Node node) {
        if (node.father == node) {
            return node;
        }
        return findFather(node.father);
    }

    @Test
    void test() {
//        int[][] tmp = matrixRankTransform(new int[][]{
//            {20, -21, 14},
//            {-19, 4, 19},
//            {22, -47, 24},
//            {-19, 4, 19},
//        });
        int[][] tmp = matrixRankTransform(new int[][]{
            {-37, -50, -3, 44},
            {-37, 46, 13, -32},
            {47, -42, -3, -40},
            {-17, -22, -39, 24},
        });
//        int[][] tmp = matrixRankTransform(new int[][]{
//            {-37, -50, -3, 44},
//            {-37, 46, 13, -32},
//            {-3, -42, -3, -3},
//            {-17, -22, -39, 24},
//        });
        for (int y = 0; y < tmp.length; ++y) {
            for (int x = 0; x < tmp[0].length; ++x) {
                System.out.print(tmp[y][x] + " ");
            }
            System.out.println();
        }
    }
}
