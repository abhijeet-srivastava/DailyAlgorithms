package com.leetcode.mothly.july23;

import java.util.Arrays;

public class LCPractice {

    public static void main(String[] args) {
        LCPractice lcp = new LCPractice();
        lcp.solve28July();
    }

    private void solve28July() {
        int[] nums = {1,5,2};
        boolean win = PredictTheWinner(nums);
        System.out.printf("Player 1 is winner: %b\n", win);
    }
    public boolean PredictTheWinner(int[] nums) {
        //return topDown(nums);
        return bottomUp(nums);
    }

    private boolean bottomUp(int[] nums) {
        int n = nums.length;
        int[][] DP = new int[n][n];
        for(int i = 0; i < n; i++) {
            DP[i][i] = nums[i];
        }
        for(int diff = 1; diff < n; diff++) {//For all the diffs
            for(int l = 0; l < n-diff; l++) {//For all the left start
                int r = l + diff;
                DP[l][r] = Math.max(
                        nums[l] - DP[l+1][r],
                        nums[r] - DP[l][r-1]
                );
            }
        }
        return DP[0][n-1] >= 0;
    }

    private boolean topDown(int[] nums) {
        int n = nums.length;
        int[][] DP = new int[n][n];
        for(int[] row: DP) {
            Arrays.fill(DP, -1);
        }
        return (topDownScoreSum(nums, 0, n-1, DP) >= 0);
    }

    private int topDownScoreSum(int[] nums, int l, int r, int[][] DP) {
        if(l == r) {
            return nums[l];
        } else if(DP[l][r] >= 0) {
            return DP[l][r];
        }
        DP[l][r] = Math.max(
                nums[l] - topDownScoreSum(nums, l+1, r, DP),//Player choose left
                nums[r] - topDownScoreSum(nums, l, r-1, DP)
        );
        return DP[l][r];
    }
}
