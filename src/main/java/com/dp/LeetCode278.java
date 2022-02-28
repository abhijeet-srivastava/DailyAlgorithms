package com.dp;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LeetCode278 {

    public static void main(String[] args) {
        LeetCode278 lc = new LeetCode278();
        //lc.testMinOp();
        //lc.testBeanRemoval();
        lc.testCountBoundedArr();
    }

    private void testCountBoundedArr() {
        int[] arr = {2,1,4,3};
        int cnt = numSubarrayBoundedMax(arr, 2, 3);
        System.out.printf("Count: %d\n", cnt);
    }

    private void testBeanRemoval() {
        int[] beans = {4,1,6,5};
        long remove = minimumRemoval(beans);
        System.out.printf("Removal: %d\n", remove);
    }

    private void testMinOp() {
        int[] nums = {3,1,3,2,4,3};
        int ops = minimumOperations(nums);
        System.out.printf("Min ops: %d\n", ops);
    }

    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int[] DP = new int[nums.length];
        int prev = -1;
        DP[0] = (nums[0] <  left || nums[0] > right) ? 0 : 1;
        System.out.printf("DP[%d] = %d\n", 0, DP[0]);
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] < left) {
                DP[i] = DP[i-1];
            } else if(nums[i] > right) {
                DP[i] = 0;
                prev = i;
            } else {
                DP[i] = DP[i-prev];
            }
            System.out.printf("DP[%d] = %d\n", i, DP[i]);
        }
        return IntStream.of(DP).sum();
    }
    public long minimumRemoval(int[] beans) {
        int sum = IntStream.of(beans).sum();
        Arrays.sort(beans);
        int len = beans.length;
        long min = sum - (len*beans[0]);
        for(int i = 1;  i < len; i++) {
            min = Math.min(min, sum - (len-i)*beans[i]);
        }
        return min;
    }
    public int minimumOperations(int[] nums) {
        int max = IntStream.of(nums).max().getAsInt()+1;
        int[][] DP = new int[max][2];
        for(int i = 0; i < nums.length; i++) {
            DP[nums[i]][i%2] += 1;
        }
        int ans = 0;
        for(int i = 1, j = 0, k = 0; i < max; i++) {
            ans = Math.max(ans, Math.max(DP[i][0] + k, DP[i][1]+j));
            j = Math.max(j, DP[i][0]);
            k = Math.max(k, DP[i][1]);
        }
        return nums.length-ans;
    }
    public String minInteger(String num, int k) {
        int len = num.length();
        int[] arr = new int[len];
        for(int i = 0; i < len; i++) {
            arr[i] = Character.getNumericValue(num.charAt(i));
        }
        int l = 0;
        while(l < len && k > 0) {
            int min = l;
            for(int i = l; i < len && i-l <= k; i++) {
                if(arr[i] < arr[min]) {
                    min = i;
                }
            }
            if(min != l) {
                int tmp = arr[min];
                for(int j = min; j-1 >= l; j--) {
                    arr[j] = arr[j-1];
                }
                arr[l] = tmp;
            }
            k -= (min-l);
            l += 1;
        }
        return IntStream.of(arr).mapToObj(String::valueOf).collect(Collectors.joining());
    }
}
