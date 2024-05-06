package com.tree;

public class SegmentTreeSum {
    int[] tree;
    int len;

    public SegmentTreeSum(int[] arr) {
        this.len = arr.length;
        this.tree = new int[getSegmentTreeSize(len)];
        buildSegmentTree(0, 0, this.len-1, arr);
    }

    public int sumRangeQuery(int l, int r) {
        return sumRangeQuery(0, 0, len-1, l, r);
    }

    public void update(int idx, int val) {
        this.update(0, 0, len-1, idx, val);
    }

    public void updateRange() {

    }
    private void update(int ti, int tl, int tr, int idx, int val) {
        if(tl == tr) {
            this.tree[ti] = val;
        }  else {
            int tm = tl + (tr-tl)/2;
            if(idx <= tm) {
                update(2*ti+1, tl, tm, idx, val);
            } else {
                update(2*ti+2, tm+1, tr, idx, val);
            }
            tree[ti] = tree[2*ti+1] + tree[2*ti+2];
        }
    }

    private int sumRangeQuery(int ti, int tl, int tr, int l, int r) {
        if(l > tr || r < tl) {
            return 0;
        } else if(l <= tl && tr <= r) {//TreeRange completely Inside query interval
            return this.tree[ti];
        }
        int tm = tl + (tr-tl)/2;
        int fh = sumRangeQuery(2*ti+1, tl, tm, l, r);
        int sh = sumRangeQuery(2*ti+2, tm+1, tr, l, r);
        return fh+sh;
    }

    private void buildSegmentTree(int ti, int tl, int tr, int[] arr) {
        if(tl == tr) {
            this.tree[ti] = arr[tl];
        } else {
            int tm = tl + (tr - tl)/2;
            buildSegmentTree(2*ti+1, tl, tm, arr);
            buildSegmentTree(2*ti+2, tm+1, tr, arr);
            this.tree[ti] = tree[2*ti+1] + tree[2*ti+2];
        }
    }

    private int getSegmentTreeSize(int len) {
        int size = 1;
        while(size <= len) {
            size <<= 1;
        }
        return size << 1;
    }

    public static void main(String[] args) {
        int[] arr = {5,18,13};
        SegmentTreeSum sts = new SegmentTreeSum(arr);
        int sum = sts.sumRangeQuery(0, 2);
        System.out.printf("0 sum range: %d\n", sum);
        sts.update(1, -1);
        System.out.printf("1 sum range: %d\n", sts.sumRangeQuery(0, 2));
        sts.update(2, 3);
        System.out.printf("2 sum range: %d\n", sts.sumRangeQuery(0, 2));
        sts.update(0, 5);
        System.out.printf("3 sum range: %d\n", sts.sumRangeQuery(0, 2));;
        sts.update(0, -4);
        sum = sts.sumRangeQuery(0, 2);
        System.out.printf("4 sum range: %d\n", sum);
    }
}
