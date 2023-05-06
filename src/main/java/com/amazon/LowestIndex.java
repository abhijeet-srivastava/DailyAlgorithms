package com.amazon;

import java.util.Arrays;

public class LowestIndex {

    public static void main(String[] args) {
        LowestIndex li = new LowestIndex();
        li.testBeautifulSubset();
    }

    private void testBeautifulSubset() {
        int[] arr = {4, 2, 5,10,9, 3 };
        int count = beautifulSubsets(arr, 1);
        System.out.printf("Count: %d\n", count);
    }

    public int beautifulSubsets(int[] nums, int k) {
        Arrays.sort(nums);
        int len = nums.length;
        for (int i = 0; i < nums.length; i++) {
            int index = lowestIndex(nums, nums[i] + k);
            if (index == -1) {
                break;
            }
            len += (nums.length - index);
        }

        return len;
    }

    private int lowestIndex(int[] nums, int val) {
        if (val >= nums[nums.length - 1]) {
            return -1;
        }
        int l = 0, r = nums.length - 1;
        int res = r;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] <= val) {
                l = mid + 1;
            } else {
                res = mid;
                r = mid - 1;
            }
        }
        return res;
    }
}

