package com.prep24;

public class BinaryBisectUtil {
    public static int bisectLeft(int[] nums, int target) {
        int i = 0;
        int j = nums.length - 1;
        while (i <= j) {
            int m = i + (j-i) / 2;
            if (nums[m] < target) {
                i = m + 1;
            } else {
                j = m - 1;
            }
        }
        return i;
    }
    public static int bisectRight(int[] nums, int target) {
        int i = 0;
        int j = nums.length - 1;
        while (i <= j) {
            int m = i + (j-i) / 2;
            if (nums[m] <= target) {
                i = m + 1;
            } else {
                j = m - 1;
            }
        }
        return j+1;
    }
    private int bisectRight(int val, int[] arr) {
        int l = 0, r = arr.length;
        while(l < r) {
            int mid = l + ((r-l)>>1);
            if(arr[mid] <= val) {
                l = mid+1;
            } else {
                r = mid;
            }
        }
        return l;
    }
    private static final Long MOD = 1_000_000_007L;
    private static long combonotrics(int n, int r, long[][] DP) {
        if(n == 0 || r == 0) {
            return 1l;
        }  else if(n == r) {
            return 1l;
        } else if(r == 1) {
            return n;
        }else if(DP[n][r] != 0) {
            return DP[n][r];
        }
        long comb = (combonotrics(n-1, r, DP) + combonotrics(n-1, r-1, DP))%MOD;

        DP[n][r] = comb;
        return comb;

    }

}
