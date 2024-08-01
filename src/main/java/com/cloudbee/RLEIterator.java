package com.cloudbee;

public class RLEIterator {
    int[] encoding;
    int countIdx;

    public RLEIterator(int[] encoding) {
        this.encoding = encoding;
        this.countIdx = 0;
    }

    public int next(int n) {
        if(countIdx >= encoding.length-1) {
            return -1;
        } else if(n <= encoding[countIdx]) {
            encoding[countIdx] -= n;
            int val = encoding[countIdx+1];
            if(encoding[countIdx] == 0) {
                countIdx += 2;
            }
            return val;
        } else if(encoding[countIdx] == 0) {
            this.countIdx += 2;
            return next(n);
        }
        int taken = Math.min(encoding[countIdx], n);
        encoding[countIdx] -= taken;
        countIdx += 2;
        n -= taken;
        return next(n);
    }

    public static void main(String[] args) {
        int[] encoding = {3,8,0,9,2,5};
        RLEIterator ri = new RLEIterator(encoding);
        System.out.printf("val: %d\n", ri.next(2));
        System.out.printf("val: %d\n", ri.next(1));
        System.out.printf("val: %d\n", ri.next(1));
        System.out.printf("val: %d\n", ri.next(2));
    }
}
