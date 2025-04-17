package com.tree;

public class RangeSumSegmentTree {
    int[] tree, arr;
    int n;

    public RangeSumSegmentTree(int[] nums) {
        this.n = nums.length;
        arr = nums;
        tree = new int[4 * n]; // Allocating 4*N space
        build(0, 0, n - 1);
    }

    private void build(int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;
            build(2 * node + 1, start, mid);
            build(2 * node + 2, mid + 1, end);
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    public int query(int left, int right) {
        return queryUtil(0, 0, n - 1, left, right);
    }

    private int queryUtil(int node, int start, int end, int left, int right) {
        if (right < start || left > end) return 0; // Out of range
        if (left <= start && end <= right) return tree[node]; // Fully in range
        int mid = (start + end) / 2;
        return queryUtil(2 * node + 1, start, mid, left, right) +
                queryUtil(2 * node + 2, mid + 1, end, left, right);
    }

    public void update(int idx, int newValue) {
        updateUtil(0, 0, n - 1, idx, newValue);
    }

    private void updateUtil(int node, int start, int end, int idx, int newValue) {
        if (start == end) {
            arr[idx] = newValue;
            tree[node] = newValue;
        } else {
            int mid = (start + end) / 2;
            if (idx <= mid) updateUtil(2 * node + 1, start, mid, idx, newValue);
            else updateUtil(2 * node + 2, mid + 1, end, idx, newValue);
            tree[node] = tree[2 * node + 1] + tree[2 * node + 2];
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 7, 9, 11};
        RangeSumSegmentTree segTree = new RangeSumSegmentTree(nums);

        System.out.println("Sum of range (1, 3): " + segTree.query(1, 3)); // Output: 15

        segTree.update(1, 10); // Update index 1 to 10
        System.out.println("Sum of range (1, 3) after update: " + segTree.query(1, 3)); // Output: 22
    }
}
