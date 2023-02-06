package com.leetcode.mothly.feb23;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LeetCodeApp {
    public static void main(String[] args) {
        LeetCodeApp lca = new LeetCodeApp();
        lca.testArrayShuffle();
    }

    private void testArrayShuffle() {
        //int[] arr = {2,5,1,3,4,7};
        //int[] arr = {1,2,3,4,4,3,2,1};
        int[] arr = {1,1,2,2};
        int[] res = shuffle(arr, arr.length/2);
        System.out.printf("[%s]\n", IntStream.of(res).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
    }

    public int[] shuffle(int[] nums, int n) {
        for(int i = n; i < 2*n; i++) {
            int secNum = nums[i] << 10;
            nums[i-n] |= secNum;
        }
        int allOnes = (int)Math.pow(2, 10)-1;
        for(int i = n-1; i >= 0; i--) {
            int yi = nums[i] >> 10;
            int xi = nums[i]&allOnes;
            nums[2*i] = xi;
            nums[2*i+1] = yi;
        }
        return nums;
    }
}
