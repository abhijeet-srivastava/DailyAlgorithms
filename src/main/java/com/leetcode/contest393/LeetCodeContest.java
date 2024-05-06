package com.leetcode.contest393;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class LeetCodeContest {

    public static void main(String[] args) {
        LeetCodeContest lcc = new LeetCodeContest();

        lcc.testFinfKthSmallest();
    }

    private void testFinfKthSmallest() {
        int[] nums = {3, 6, 9};
        long res = findKthSmallest(nums, 3);


    }

    public long findKthSmallest(int[] coins, int k) {
        int n = coins.length;
        List<Integer>[] comb = new ArrayList[n+1];
        for(int i = 0; i <= n; i++) {
            comb[i] = new ArrayList<>();
        }
        for(int m = 1; m <= (1 << n); m++) {
            int cnt  = 0, v =  1;
            for(int i = 0; i < n; i++) {
                if((m & (1<<i)) != 0) {
                    cnt += 1;
                    int g = BigInteger.valueOf(v).gcd(BigInteger.valueOf(coins[i])).intValue();
                    v *= coins[i]/g;
                }
            }
            comb[cnt].add(v);
        }
        return -1;
    }
}
