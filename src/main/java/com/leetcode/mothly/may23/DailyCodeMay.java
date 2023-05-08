package com.leetcode.mothly.may23;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DailyCodeMay {

    public static void main(String[] args) {
        DailyCodeMay dcm = new DailyCodeMay();
        //dcm.test6May();
        //dcm.test7May();
        dcm.testContest344_1();
    }

    private void testContest344_1() {
        int[] arr = {1,2,3,4,5};
        int[] res = distinctDifferenceArray(arr);
        System.out.printf("[%s]\n", IntStream.of(res).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
    }

    private void test7May() {
        int[] arr = {3,1,5,6,4,2};
        int[] res = longestObstacleCourseAtEachPosition(arr);
        System.out.printf("[%s]\n", IntStream.of(res).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
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
    public int[] longestObstacleCourseAtEachPosition(int[] nums) {
        int n = nums.length, len = 1;
        int[] DP = new int[n];
        int[] res = new int[n];
        DP[0] = nums[0];
        res[0] = 1;
        for(int i = 1; i < nums.length; i++) {
            if(DP[len-1] <= nums[i]) {
                DP[len] = nums[i];
                len += 1;
                res[i] = len;
            }else if(nums[i] <= DP[0]) {
                DP[0] = nums[i];
                res[i] = 1;
            }  else {
                int ip = Arrays.binarySearch(DP, 0, len-1, nums[i]);
                if(ip < 0) {
                    ip = -(ip+1);
                    res[i] = ip+1;
                } else {
                    res[i] = ip+2;
                }
                DP[ip] = nums[i];
            }
        }
        return res;
    }

    public int[] distinctDifferenceArray(int[] nums) {
        int n = nums.length;
        int[] counter = new int[51];
        int[] suffix = new int[n], prefix =  new int[n];
        prefix[0] = 1;
        suffix[n-1] = 1;
        counter[nums[0]] = 1;
        for(int i = 1; i < n; i++) {
            prefix[i] = prefix[i-1] + (counter[nums[i]] > 0 ? 0 : 1);
            counter[nums[i]] += 1;
        }
        Arrays.fill(counter, 0);
        counter[nums[n-1]] = 1;
        for(int i = n-2; i >= 0; i--) {
            suffix[i] = suffix[i+1] + (counter[nums[i]] > 0 ? 0 : 1);
            counter[nums[i]] += 1;
        }
        int[] res = new int[n];
        for(int i = 0; i < n-1; i++) {
            res[i] = prefix[i] - suffix[i+1];
        }
        res[n-1] = prefix[n-1];
        return res;

    }
    public int diagonalSum(int[][] mat) {
        int n  = mat.length;
        int sum = 0;
        for(int i = 0; i < n; i++) {
            sum += mat[i][i] + mat[i][n-i-1];
        }
        if((n&1) != 0) {
            sum -= mat[n/2][n/2];
        }
        return sum;
    }

}
