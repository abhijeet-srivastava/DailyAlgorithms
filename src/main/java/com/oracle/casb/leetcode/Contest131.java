package com.oracle.casb.leetcode;

import java.util.ArrayList;
import java.util.List;

public class Contest131 {

    public List<Boolean> camelMatch(String[] queries, String pattern) {
        List<Boolean> res = new ArrayList<>(pattern.length());
        char[] patterArr = pattern.toCharArray();
        for (String query : queries) {
            res.add(isMatch(query.toCharArray(), patterArr));
        }
        return res;
    }

    private Boolean isMatch(char[] queryArr, char[] patterArr) {
        int j = 0;
        for (int i = 0; i < queryArr.length; i++) {
            if (j < patterArr.length && queryArr[i] == patterArr[j]) {
                j += 1;
            } else if (Character.isUpperCase(queryArr[i])) {
                return false;
            }
        }
        return j == patterArr.length;
    }


    public void sortColors(int[] nums) {
        int p0 = 0;
        int p1 = nums.length -1;
        int i = 0;
        while (i <= p1) {
            if (nums[i] == 0) {
                swap(nums, i, p0);
                i += 1;
                p0 += 1;
            } else if (nums[i] == 2) {
                swap(nums, i, p1);
                p1 -= 1;
            } else {
                i += 1;
            }
        }
    }

    private void swap(int[] nums, int i, int p0) {
        int tmp = nums[i];
        nums[i] = nums[p0];
        nums[p0] = tmp;
    }
}
