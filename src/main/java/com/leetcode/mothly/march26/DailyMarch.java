package com.leetcode.mothly.march26;

import java.util.Arrays;

public class DailyMarch {

    public static void main(String[] args) {
        DailyMarch dm = new DailyMarch();
        dm.solve16March();
    }

    private void solve16March() {
        int[][] matrix = {{0,0,1}, {1,1,1},{1,0,1}};
        int max = largestSubmatrix(matrix);
        System.out.printf("Res=%d\n", max);
    }

    public int largestSubmatrix(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        for(int r = 1; r < m; r++) {
            for(int c = 0; c < n;c++) {
                if(matrix[r][c] == 1) {
                    matrix[r][c] = 1 + matrix[r-1][c];
                }
            }
        }
        int res = 0;
        for(int[] row: matrix) {
            Arrays.sort(row);
            for(int c = n-1; c >= 0; c--) {
                res = Math.max(res, row[c]*(n-c));
            }
        }

        return res;
    }
}
