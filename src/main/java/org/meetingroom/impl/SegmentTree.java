package org.meetingroom.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import java.util.List;

public class SegmentTree {
    static class SegmentNode {
        int left;
        int right;
        boolean value = false;
    }

    @VisibleForTesting
    final SegmentNode[] tree;
    private final int max;

    public SegmentTree(int max) {
        tree = new SegmentNode[max * 4 + 1];
        for (int idx = 0; idx < max * 4 + 1; ++idx) {
            tree[idx] = new SegmentNode();
        }
        this.max = max;
    }

    public void add(int left, int right) {
        Preconditions.checkArgument(left >= 0 && left <= max, left);
        Preconditions.checkArgument(right >= 0 && right <= max, right);
        doAdd(left, right, 0, max, 1);
    }

    private void doAdd(int currentLeft, int currentRight, int leftRange, int rightRange, int idx) {
        if (currentLeft == leftRange && currentRight == rightRange) {
            tree[idx].left = currentLeft;
            tree[idx].right = currentRight;
            tree[idx].value = true;
            return;
        }
        int mid = (leftRange + rightRange) / 2;
        if (currentLeft > mid) {
            doAdd(currentLeft, currentRight, mid + 1, rightRange, idx * 2 + 1);
        } else if (currentRight <= mid) {
            doAdd(currentLeft, currentRight, leftRange, mid, idx * 2);
        } else {
            doAdd(currentLeft, mid, leftRange, mid, idx * 2);
            doAdd(mid + 1, currentRight, mid + 1, rightRange, idx * 2 + 1);
        }
    }

    public boolean query(int left, int right) {
        Preconditions.checkArgument(left >= 0 && left <= max, left);
        Preconditions.checkArgument(right >= 0 && right <= max, right);
        return doQuery(left, right, 0, max, 1);
    }

    private boolean doQuery(int currentLeft, int currentRight, int leftRange, int rightRange, int idx) {
        if (currentLeft == leftRange && currentRight == rightRange) {
            return tree[idx].value;
        }

        if (tree[idx].value && currentLeft >= leftRange && currentRight <= rightRange) {
            return true;
        }

        int mid = (leftRange + rightRange) / 2;
        if (currentLeft > mid) {
            return doQuery(currentLeft, currentRight, mid + 1, rightRange, idx * 2 + 1);
        } else if (currentRight <= mid) {
            return doQuery(currentLeft, currentRight, leftRange, mid, idx * 2);
        } else {
            return doQuery(currentLeft, mid, leftRange, mid, idx * 2)
                ||
                doQuery(mid + 1, currentRight, mid + 1, rightRange, idx * 2 + 1);
        }
    }

    public List<SegmentNode> getAllAdded() {
        List<SegmentNode> answer = Lists.newArrayList();
        doGetAllAdded(0, max, 1, answer);
        return answer;
    }

    private void doGetAllAdded(int left, int right, int idx, List<SegmentNode> answer) {
        if (tree[idx].value) {
            answer.add(tree[idx]);
            return;
        }
        if (left == right) {
            return;
        }
        int mid = (left + right) / 2;
        doGetAllAdded(left, mid, idx * 2, answer);
        doGetAllAdded(mid + 1, right, idx * 2 + 1, answer);
    }

    public void remove(int left, int right) {
        //TODO
    }
}
