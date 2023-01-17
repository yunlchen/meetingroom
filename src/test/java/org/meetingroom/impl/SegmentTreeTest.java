package org.meetingroom.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SegmentTreeTest {
    @Test
    void test() {
        SegmentTree segmentTree = new SegmentTree(16);
        segmentTree.add(2, 8);
        Assertions.assertTrue(segmentTree.tree[17].value);
        Assertions.assertTrue(segmentTree.tree[9].value);
        Assertions.assertTrue(segmentTree.tree[5].value);

        Assertions.assertTrue(segmentTree.query(3, 4));
        Assertions.assertTrue(segmentTree.query(5, 9));
        Assertions.assertTrue(segmentTree.query(8, 9));
        Assertions.assertTrue(segmentTree.query(1, 2));
        Assertions.assertTrue(segmentTree.query(2, 2));
        Assertions.assertTrue(segmentTree.query(3, 3));
        Assertions.assertTrue(segmentTree.query(4, 4));
        Assertions.assertTrue(segmentTree.query(5, 5));
        Assertions.assertTrue(segmentTree.query(6, 6));
        Assertions.assertTrue(segmentTree.query(7, 7));
        Assertions.assertTrue(segmentTree.query(8, 8));

        Assertions.assertFalse(segmentTree.query(1, 1));
        Assertions.assertFalse(segmentTree.query(9, 12));
    }
}
