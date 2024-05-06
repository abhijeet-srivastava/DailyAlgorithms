package com.tree;

import java.util.Arrays;

public class SegmentTreeIC {

    //private int[] array;
    int len;
    private int[] tree;

    private int size;

    public SegmentTreeIC(int[] arr) {
        //this.array = Arrays.copyOf(arr, arr.length);
        this.len = arr.length;
        this.size = getSegmentTreeSize(arr.length);
        this.tree = new int[size];
        buildTree(0,  0, arr.length-1, arr);
    }

    public int rangeMinQuery(int left, int right) {
        return rangeMinQuery(0, 0, len-1, left, right);
    }
    public void update(int idx, int val) {
        update(0, 0, len-1,  idx,  val);
    }

    private void update(int ti, int leftRange, int rightRange, int idx, int val) {
        if(leftRange == rightRange) {
            this.tree[ti] = val;
        } else {
            int mid = leftRange + (rightRange - leftRange)/2;
            if(idx <= mid) {
                //Update left subtree of segment tree
                update(ti*2+1, leftRange, mid, idx, val);
            } else {
                update(ti*2+2, mid+1, rightRange, idx, val);
            }
            this.tree[ti] = Math.min(tree[2*ti+1], tree[2*ti+2]);
        }
    }

    private void buildTree(int ti,  int left, int right, int[] array) {
        if(left == right) {
            tree[ti] = array[left];
        } else {
            int mid = left + (right - left)/2;
            buildTree(2*ti+1, left, mid, array);
            buildTree(2*ti+2, mid+1, right, array);
            tree[ti] = Math.min(tree[2*ti+1], tree[2*ti+2]);
        }
    }
    private int rangeMinQuery(int ti, int leftRange, int rightRange, int l, int r) {
        if(l > rightRange || r < leftRange) {//Completely out
            return Integer.MAX_VALUE;
        } else if(l <= leftRange && rightRange <= r) {
            //Range represented by Node is inside given range
            return this.tree[ti];
        } else {
            //Partial overlap
            int mid = leftRange + (rightRange - leftRange) /2;
            int firstHalf = rangeMinQuery(2*ti+1, leftRange, mid, l, r);
            int secondHalf = rangeMinQuery(2*ti+2, mid+1, rightRange, l, r);
            return Math.max(firstHalf, secondHalf);
        }
        /*if(l == leftRange && r == rightRange) {
            return this.tree[ti];
        }
        int mid = leftRange + (rightRange - leftRange) /2;
        int firstHalf = rangeMinQuery(2*ti+1, leftRange, mid, l, Math.min(mid, r));
        int secondHalf = rangeMinQuery(2*ti+2, mid+1, rightRange, Math.max(mid+1, l), r);
        return Math.max(firstHalf, secondHalf);*/
    }

    private int getSegmentTreeSize(int n) {
        int size = 1;
        while(size < n) {
            size = size << 1;
        }
        return size << 1;
    }

}
