package com.walmart;

public class KnapSack {

    private int knapSack(int W, int[] weights, int[] values) {
        int n = weights.length;
        int[][] DP = new int[n+1][W+1];
        for(int i = 0; i <= n; i++) {
            for(int w = 0; w <= W; w++) {
                if(i == 0 || w == 0) {
                    DP[i][w] = 0;
                    continue;
                }
                if(weights[i-1] > w) {//Can't be considered
                    DP[i][w] = DP[i-1][w];
                } else {
                    DP[i][w] = Math.max(values[i-1] + DP[i-1][w - weights[i-1]], DP[i-1][w]);
                }
            }
        }
        return DP[n][W];
    }
    //Coin Change: https://geeksforgeeks.org/coin-change-dp-7/
    private int cointChange(int[] denominations, int value) {
        int[] DP = new int[value+1];
        DP[0] = 1;//There is only one way to have sum - 0; include no coins
        for(int i = 0; i <= denominations.length; i++) {
            for(int j = denominations[i]; j <= value; i++) {
                DP[j] += DP[j - denominations[i]];
            }
        }
        return DP[value];
    }

    private int coinChange(int[] denominations, int value) {
        int[][] DP = new int[denominations.length+1][value+1];
        for(int i = 0; i <= denominations.length; i++) {
            //for(int j = 0; j <= value; j++) {
            for(int j = denominations[i]; j <= value; j++) {
                if(i == 0) {
                    DP[i][j] = 0;
                    continue;
                } else if(j == 0) {
                    DP[i][j] = 1;
                    continue;
                }
                if(denominations[i] > j) {
                    DP[i][j] = DP[i-1][j];
                } else {
                    DP[i][j] = DP[i-1][j] + DP[i][j-denominations[i]];
                }
            }
        }

        return DP[denominations.length][value];
    }
}
