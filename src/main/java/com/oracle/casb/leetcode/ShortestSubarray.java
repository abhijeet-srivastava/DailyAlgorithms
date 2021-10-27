package com.oracle.casb.leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created By : abhijsri
 * Date  : 04/07/18
 **/
public class ShortestSubarray {

    public static void main(String[] args) {
        ShortestSubarray ss = new ShortestSubarray();
        ss.solveSubarray();
    }

    private void solveSubarray() {
        int[] array = new int[] {1,-3,2,1,0,-2,1,0,1};
        int len = getShortestSubArrayLen(array, 3);
    }

    private int getShortestSubArrayLen(int[] array, int K) {
        int len = array.length;
        int shortestLen = len + 1;
        // Want smallest y-x with P[y] - P[x] >= K
        long[] P = new long[len + 1];
        P[0] = 0l;
        for (int i = 0; i < len; i++) {
            P[i+1] = P[i] + (long) array[i];
        }
        Deque<Integer> deque = new LinkedList<>();//opt(y) candidates, as indices of P
        for (int y = 0; y <= len; ++y){
            // Want opt(y) = largest x with P[x] <= P[y] - K;
            while (!deque.isEmpty() && P[y] <= P[deque.getLast()]) {
                deque.removeLast();
            }
            while(!deque.isEmpty() && P[y] >= P[deque.getFirst()] + K) {
                shortestLen = Math.min(shortestLen, y- deque.getFirst());
                deque.removeFirst();
            }
            deque.addLast(y);
        }

        return shortestLen >= (len + 1) ? -1 : shortestLen;
    }
}
