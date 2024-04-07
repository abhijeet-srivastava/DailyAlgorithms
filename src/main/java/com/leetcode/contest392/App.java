package com.leetcode.contest392;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        App app = new App();
        //app.testFirst();
       // app.testSmallestString();
        //app.testMedian();
        app.testCheckValidString();
    }

    private void testCheckValidString() {
        //String str  = "(((((()*)(*)*))())())(()())())))((**)))))(()())()";
        String str  = "(((((*(()((((*((**(((()()*)()()()*((((**)())*)*)))))))(())(()))())((*()()(((()((()*(())*(()**)()(())";
        boolean isValid = checkValidString(str);
        System.out.printf("valid: %b\n", isValid);
    }

    private void testMedian() {
        int[] nums = {98,52};
        long val = minOperationsToMakeMedianK(nums, 82);
    }
    public boolean checkValidString(String s) {
        int n = s.length();
        boolean[][] DP= new  boolean[n+1][n+1];
        DP[n][0] = true;//if (index == s.size()) return (openingBracket == 0);
        for(int idx = n-1; idx >= 0; idx--) {
            char ch = s.charAt(idx);
            for(int count = 0; count < n; count++) {
                if(ch == '(') {
                    DP[idx][count] = DP[idx+1][count+1];
                } else if(ch == ')' && count > 0) {
                    DP[idx][count] = DP[idx+1][count-1];
                } else if(ch == '*'){
                    DP[idx][count] = DP[idx+1][count] || DP[idx+1][count+1];
                    if(count > 0) {
                        DP[idx][count] =  DP[idx][count] || DP[idx+1][count-1];
                    }
                }
            }
        }
        return DP[0][0];
    }

    public long minOperationsToMakeMedianK(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        long res = 0l;
        for(int i = 0; i <= n/2; i++) {
            res += Math.max(0, nums[i]-k);
        }
        for(int i = n/2; i < n; i++) {
            res += Math.max(0, k- nums[i]);
        }
        return res;
    }

    private void testSmallestString() {
        String str= "xaxcd";
        String smallest = getSmallestString(str, 4);
        System.out.printf("Smallest: %s\n", smallest);
    }

    public String getSmallestString(String s, int k) {
        if(k == 0) {
            return s;
        }
        int n = s.length();
        char[] arr = new char[n];
        for(int i = 0; i < n; i++) {
            int currIdx = s.charAt(i) - 'a';
            int nextADist = 26-currIdx,  prevIdx = currIdx;
            if(nextADist <= k || prevIdx <= k) {
                arr[i] = 'a';
                k -= Math.min(nextADist, prevIdx);
            } else {
                arr[i] = (char)(currIdx-k + 'a');
                k = 0;
            }
        }
        return new String(arr);
    }
    private void testFirst() {
        int[] arr = {3,2,1};
        System.out.printf("max: %d\n", longestMonotonicSubarray(arr));
    }
    public int longestMonotonicSubarray(int[] nums) {
        int lis = 1, lds = 1;
        int prev = nums[0];
        int currMax = 1, currMin = 1;
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] > prev) {
                currMax += 1;
                lis = Math.max(lis, currMax);
            } else {
                currMax =  1;
            }
            if(nums[i] < prev) {
                currMin += 1;
                lds = Math.max(lds, currMin);
            } else {
                currMin =  1;
            }

            prev = nums[i];
        }
        return Math.max(lis, lds);
    }

    private int longestDecreasingSubseq(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // array to store subproblem solution. `L[i]` stores the length
        // of the longest decreasing subsequence that ends with `nums[i]`
        int[] L = new int[nums.length];
        // longest decreasing subsequence ending at `nums[0]` has length 1
        L[0] = 1;
        // start from the second array element
        for (int i = 1; i < nums.length; i++) {
            // do for each element in subarray `nums[0…i-1]`
            for (int j = 0; j < i; j++) {
                // find longest decreasing subsequence that ends with `nums[j]`
                // where `nums[j]` is more than the current element `nums[i]`

                if (nums[j] > nums[i] && L[j] > L[i]) {
                    L[i] = L[j];
                }
            }
            // include `nums[i]` in LDS
            L[i]++;
        }

        // return longest decreasing subsequence (having maximum length)
        return Arrays.stream(L).max().getAsInt();
    }
    private int longestDecreasingSubseq(int[] nums, int idx, int prev) {
       if(idx == nums.length) {
           return 0;
       }
        int excl = longestDecreasingSubseq(nums, idx + 1, prev);
        int incl = 0;
        if (nums[idx] < prev) {
            incl = 1 + longestDecreasingSubseq(nums, idx + 1, nums[idx]);
        }
        return Math.max(incl, excl);
    }

    private int longestIncreasingSubseq(int[] nums) {
        int n = nums.length;
        int[] DP = new int[n];
        DP[0] = nums[0];
        int l = 1;
        for(int i = 1; i < n; i++) {
            if(nums[i] > DP[l-1]) {
                DP[l] = nums[i];
                l += 1;
            } else {
                int idx = Arrays.binarySearch(DP, 0, l-1, nums[i]);
                if(idx < 0) {
                    idx = -(idx+1);
                }
                DP[idx] = nums[i];
            }
        }
        return l;
    }
}
