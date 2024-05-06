package com.tree;

public class BinaryIndexedTree {
    //Last bit set of number = x&(-x)
    //BIT[i] = resp i  i -RSB[i] + 1
    private int[] BIT;
    public static void main(String[] args) {
        int[] arr = {1,3,5};
        BinaryIndexedTree bit = new BinaryIndexedTree(arr);
        //bit.testRangeQueries();
        bit.testReadValues();

    }

    private void testReadValues() {
        update(1, 2);
        for(int i = 1; i < BIT.length; i++) {
            //System.out.printf("arr[%d] = %d\n", i-1, readSingle(i));
            System.out.printf("arr[%d] = %d\n", i-1, prefixSum(i)-prefixSum(i-1));
        }
        int left = 0, right = 2;
        int l = left == 0 ? 0: prefixSum(left);
        int r = prefixSum(right+1);
        System.out.printf("Range sum(0, 2) = %d",r-l);
    }

    public BinaryIndexedTree(int[] arr) {
        this.BIT = new int[arr.length+1];
        for(int i = 1; i <= arr.length; i++) {
            update(i, arr[i-1]);
        }
    }

    private void testRangeQueries() {
        int[][] queries = {{0, 5}, {2, 3}, {2, 4}, {1,3}, {0, 3}};
        for(int[] query : queries) {
            int l = query[0] == 0 ? 0: prefixSum(query[0]);
            int r = prefixSum(query[1]+1);
            System.out.printf("Sum[%d...%d] = %d\n", query[0], query[1], r-l);
        }
    }

    private void testBITFunction() {
        for(int i = 1; i <= 20; i++) {
            int rsb = i & -i;
            System.out.printf("Last bit set of %d: %d\n", i, rsb);
            System.out.printf("Handle [%d...%d]\n", i-rsb+1, i);
        }
    }
    private int prefixSum(int x) {
        int sum = 0;
        while(x > 0) {
            sum += BIT[x];
            x -= lsb(x);
        }
        return sum;
    }
    public void update(int x, int delta) {
        while(x < BIT.length) {
            BIT[x] += delta;
            x += lsb(x);
        }
    }

    public void updateRange(int l, int r, int delta) {
        this.update(l, delta);
        this.update(r+1, -delta);
    }

    private static int lsb(int i) {
        // Isolates the lowest one bit value
        return i & -i;
        //return Integer.lowestOneBit(i);
    }
    //Getting actual value at some index
    private int readSingle(int idx) {
        int sum = BIT[idx]; // this sum will be decreased
        if (idx > 0) { // the special case
            int z = idx - (idx & -idx);
            idx--; // idx is not important anymore, so instead y, you can use idx
            while (idx != z) { // at some iteration idx (y) will become z
                sum -= BIT[idx];
                // substruct tree frequency which is between y and "the same path"
                idx -= (idx & -idx);
            }
        }
        return sum;
    }
}
