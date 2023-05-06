package com.leetcode.mothly.may23;

import java.util.Arrays;

public class DailyCodeMay {

    public static void main(String[] args) {
        DailyCodeMay dcm = new DailyCodeMay();
        dcm.test6May();
    }

    private void test6May() {
        int[] nums = {2,3,3,4,6,7};
        int target = 12;
        int res = numSubseq(nums, target);
        System.out.printf("res: %d\n", res);
    }

    public int numSubseq(int[] nums, int target) {
        long mod = (long)1e9+7; int n = nums.length;
        long[] pow = new long[n];
        pow[0] = 1l;
        for(int i = 1; i< n; i++) {
            pow[i] = (pow[i-1]<<1)%mod;
        }
        long res = 0l;
        Arrays.sort(nums);
        int l = 0, r = n-1;
        while(l <= r) {
            int total = nums[l] + nums[r];
            //System.out.printf("l: %d, r = %d\n", nums[l], nums[r]);
            if(total <= target) {
                //System.out.printf("pow[r-l] = %d\n", pow[r-l]);
                res += pow[r-l];
                res %= mod;
                l += 1;
            } else {
                r -= 1;
            }
        }
        return (int)res;
    }

}
