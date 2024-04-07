package com.amazon;

public class PatternMatchingZArray {
    public static void main(String[] args) {
        PatternMatchingZArray matchingZArray = new PatternMatchingZArray();
        matchingZArray.match();
    }

    private void match() {
        int[] nums = {1,4,4,1,3,5,5,3}, pattern = {1, 0, -1};
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
        int[] zArr = zFunction(target);
        for(int val: zArr) {
            if(val == m) {
                count += 1;
            }
        }
        return count;
    }
    private int[] z_function(int[] arr) {
        int n = arr.length;
        int[] z = new int[n];
        int l = 0, r = 0;
        for(int i = 1; i < n; i++) {
            if(i < r) {
                z[i] = Math.min(r - i, z[i - l]);
            }
            while(i + z[i] < n && arr[z[i]] == arr[i + z[i]]) {
                z[i]++;
            }
            if(i + z[i] > r) {
                l = i;
                r = i + z[i];
            }
        }
        return z;
    }

    private int[] zFunction(int[] arr) {
        int n = arr.length;
        int[] z = new int[n];
        int l = 0;
        for(int i = 1; i < n; i++) {
            z[i] = Math.min(z[l] + l -i, z[i-l]);
            z[i] = Math.max(0, z[i]);
            while(i + z[i] < n && arr[z[i]] == arr[i+z[i]]) {
                z[i]++;
            }
            if(i + z[i] > l + z[i]) {
                l = i;
            }
        }
        return z;
    }
    private int[] zFunction(String str) {
        int n = str.length();
        int[] z = new int[n];
        int l = 0;
        for(int i = 1; i < n; i++) {
            z[i] = Math.min(z[l] + l -i, z[i-l]);
            z[i] = Math.max(0, z[i]);
            while(i + z[i] < n && str.charAt(z[i]) == str.charAt(i+z[i])) {
                z[i]++;
            }
            if(i + z[i] > l + z[i]) {
                l = i;
            }
        }
        return z;
    }

    private int[] calculateZArr(int[] nums) {
        int n = nums.length;
        int[] zArr = new int[n];
        int l = 0, r = 0;
        for(int i = 1; i < n; i++) {
            if(i < r) {
                zArr[i] = Math.min(r-i, zArr[i-l]);
            }
            while(i + zArr[i] < n && nums[zArr[i]] == nums[i + zArr[i]]) {
                zArr[i] += 1;
            }
            if(i + zArr[i] > r) {
                l = i;
                r = i + zArr[i];
            }
        }
        return zArr;
    }
    private int[] calculateZArr1(int[] target) {
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
