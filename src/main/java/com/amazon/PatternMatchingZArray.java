package com.amazon;

public class PatternMatchingZArray {
    public static void main(String[] args) {
        PatternMatchingZArray matchingZArray = new PatternMatchingZArray();
        matchingZArray.match();
    }

    private void match() {
        int[] nums = {1,4,4,1,3,5,5,3}, pattern = {-1, 0, 1};
        int count = countMatchingSubarrays(nums, pattern);
        System.out.printf("Count: %d\n", count);
    }
    public int countMatchingSubarrays(int[] nums, int[] pattern) {
        int n = nums.length, m = pattern.length;
        int[] target = new int[m+n];
        for(int i = 0; i < m; i++) {
            target[i] = pattern[i];
        }
        target[m] = 2;
        for(int i = 0; i < n-1; i++) {
            target[m+i+1] = Integer.compare(nums[i+1], nums[i]);
            //System.out.printf("nums[%d] = %d, nums[%d] = %d, target[%d] = %d\n", i+1, nums[i+1], i, nums[i], i, target[i]);
        }
        int count = 0;
        int[] zArr = calculateZArr(target);
        for(int val: zArr) {
            if(val == m) {
                count += 1;
            }
        }

        return count;
    }
    private int[] calculateZArr(int[] target) {
        int m = target.length;
        int[] zArr = new int[m];
        int L = 0, R = 0;
        for(int i = 1; i < m; i++) {
            if(i > R) {
                L = R = i;
                while(R < m && target[R - L] == target[R]) {
                    R += 1;
                }
                zArr[i] = R - L;
                R--;
            } else {
                int k = i - L;
                if(zArr[k] < R - i + 1) {
                    zArr[i] = zArr[k];
                } else {
                    L = i;
                    while(R < m && target[R - L] == target[R]) {
                        R += 1;
                    }
                    zArr[i] = R - L;
                    R--;
                }
            }
        }
        return zArr;
    }
}
