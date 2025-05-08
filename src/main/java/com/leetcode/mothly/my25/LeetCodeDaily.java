package com.leetcode.mothly.my25;

import java.util.Arrays;
import java.util.PriorityQueue;

public class LeetCodeDaily {

    public int minTimeToReach(int[][] moveTime) {
        int m = moveTime.length, n = moveTime[0].length;
        int[][] mttr = new int[m][n];
        for(int[] row: mttr) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        mttr[0][0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(a[0], b[0]));
        int[][] dirs = {{0,1}, {1, 0}, {0,-1}, {-1,0}};
        pq.offer(new int[]{0, 0, 0, 1});
        while(!pq.isEmpty()) {
            int[] curr = pq.poll();
            int ct = curr[0], cx = curr[1], cy = curr[2], cd = curr[3];
            if(cx == m-1 && cy == n-1) {
                return ct;
            }
            int nd = (cd == 1) ? 2:1;
            for(int[] dir: dirs) {
                int nx = cx + dir[0], ny = cy  + dir[1];
                if(nx < 0 || nx >= m || ny < 0 || ny >= n) {
                    continue;
                }
                int ttr = Math.max(ct, moveTime[nx][ny]) + cd;
                if(ttr < mttr[nx][ny]) {
                    mttr[nx][ny] = ttr;
                    pq.offer(new int[]{ttr, nx, ny, nd});
                }
            }
        }
        return -1;
    }
}
