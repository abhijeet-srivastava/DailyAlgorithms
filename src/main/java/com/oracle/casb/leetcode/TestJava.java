package com.oracle.casb.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created By : abhijsri
 * Date  : 2019-01-21
 **/
public class TestJava {

    public static void main(String[] args) {
        //foo("Tests it");
        TestJava tj = new TestJava();
        //tj.testArea();
        //tj.testTemp();
        tj.testPerm();
        //tj.testValidIp();
       // tj.testMaxTurb();

    }

    private void testMaxTurb() {
        int[] arr = {0,8,45,88,48,68,28,55,17,24};
        System.out.println("Max len" + maxTurbulenceSize(arr));
    }

    private int maxTurbulenceSize(int[] arr) {
        int len = arr.length;
        int maxLen = 0;
        int anchor = 0;
        for (int i = 0; i < len; i++) {
            int c = Integer.compare(arr[i-1], arr[i]);
            if (c == 0) {
                anchor = c;
            } else if (i == len-1 || c * Integer.compare(arr[i], arr[i+1]) != -1) {
                maxLen = Math.max(maxLen, i - anchor + 1);
                anchor = i;
            }
        }
        return maxLen;
    }

    public int maxTurbulenceSize1(int[] A) {
        int maxLen = A.length > 0 ? 1 : 0;
        int prev = A[0];
        int i = 1;
        while(i < A.length && prev == A[i]) {
            prev = A[i];
            i += 1;
        }
        if (i == A.length) {
            return maxLen;
        }
        int startFrom = i-1;
        boolean lastSign = A[startFrom] < A[i] ? Boolean.TRUE.booleanValue() : Boolean.FALSE.booleanValue();
        maxLen = 1;
        for (i = startFrom+2; i < A.length; i++) {
            if (inSequence(A, i, lastSign)) {
                lastSign = negate(lastSign);
            } else {
                maxLen = Math.max(maxLen, i-startFrom);
                prev = A[i-1];
                while(i < A.length && prev == A[i]) {
                    prev = A[i];
                    i += 1;
                }
                startFrom = i-1;
                if (i == A.length) {
                    break;
                }
                lastSign = A[startFrom] < A[i] ? Boolean.TRUE.booleanValue() : Boolean.FALSE.booleanValue();
            }
        }
        return maxLen;
    }

    private boolean inSequence(int[] a, int i, boolean lastSign) {
        return (lastSign && (a[i-1] > a[i]))
                || (!lastSign && (a[i-1] < a[i]));
    }

    private boolean negate(boolean lastSign) {
        return lastSign ? false : true;
    }

    private void testValidIp() {
        String s = "25525511135";
        List<String> res =restoreIpAddresses(s);
        System.out.println(res.stream().collect(Collectors.joining("\n")));
    }

    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        if (s == null || s.length() < 4) {
            return res;
        }
        List<String> segments = new ArrayList<>();
        backtrack(s, -1, 3, res , segments);
        return res;
    }

    private void backtrack(String s, int prevIndex, int pendingDots, List<String> result, List<String> segments) {
        int maxPos = Math.min(prevIndex+4, s.length()-1);
        for (int currPos = prevIndex+1; currPos < maxPos; currPos++) {
            String segment = s.substring(prevIndex+1, currPos+1);
            if (isValid(segment)) {
                segments.add(segment);
                if (pendingDots == 1) {
                    String lastSeg = s.substring(currPos+1);
                    if (isValid(lastSeg)) {
                        segments.add(s.substring(currPos+1));
                        result.add(segments.stream().collect(Collectors.joining(".")));
                        segments.remove(segments.size() - 1);
                    }
                } else {
                    backtrack(s, currPos, pendingDots - 1, result, segments);
                }
                segments.remove(segments.size() - 1);
            }
        }
    }
    private boolean isValid(String segment) {
        int m = segment.length();
        if (m > 3) {
            return false;
        }
        return (segment.charAt(0) != '0') ? (Integer.valueOf(segment) <= 255) : (m == 1);
    }

    public int[] sortedSquares(int[] A) {
        return Arrays.stream(A).map(Math::abs).sorted().map(a -> a*a).toArray();
    }

    private void testPerm() {
        int[] nums = {1,2,3,4};
        List<List<Integer>> res = permute(nums);
        for (List<Integer> perm : res) {
            System.out.println(perm.stream().map(String::valueOf).collect(Collectors.joining(",")));
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        return permute(0, nums);
    }

    private List<List<Integer>> permute(int first, int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (first == nums.length) {
            res.add(getAsList(nums));
            return res;
        }
        for (int i = first; i < nums.length; i++) {
            swap(nums, first, i);
            res.addAll(permute(first+1, nums));
            swap(nums, i, first);
        }
        return res;
    }

    private List<Integer> getAsList(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int i : nums) {
            list.add(i);
        }
        return list;
    }

    private void swap(int[] nums, int first, int i) {
        int temp = nums[first];
        nums[first] = nums[i];
        nums[i] = temp;
    }

    private void testTemp() {
        int[] T = {89,62,70,58,47,47,46,76,100,70};
        int[] res = dailyTemperatures(T);
        System.out.println(Arrays.asList(T));
    }

    private void testArea() {
        int[] heights = {2,1,5,6,2,3};
        int max = largestRectangleArea(heights);
        System.out.println(max);
    }

    public int[] dailyTemperatures(int[] T) {
        int n = T.length;
        int[] right = new int[n];
        Stack<Integer[]> stack = new Stack<>();
        for (int i = n-1; i >= 0; i--) {
            int days = 1;
            boolean flag = false;
            while (!stack.isEmpty() && stack.peek()[0] <= T[i]) {
                days += stack.pop()[1];
                flag = true;
            }
            if (stack.isEmpty()) {
                right[i] = 0;
            } else {
                right[i] = days;
            }
            stack.push(new Integer[]{T[i], days});
            //days = flag ? days : 0;
        }
        return right;
    }

    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Stack<Integer[]> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            int width = 1;
            while (!stack.isEmpty() && stack.peek()[0] >= heights[i]) {
                width += stack.pop()[1];
            }
            stack.push(new Integer[]{heights[i], width});
            left[i] = width;
        }
        stack.clear();
        for (int i = n-1; i >= 0; i--) {
            int width = 1;
            while (!stack.isEmpty() && stack.peek()[0] >= heights[i]) {
                width += stack.pop()[1];
            }
            stack.push(new Integer[]{heights[i], width});
            right[i] = width;
        }
        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            int area = heights[i] * (left[i] + right[i] - 1);
            maxArea = Math.max(maxArea, area);
        }
        return maxArea;
    }

    /**
     * res = sum(A[i] * f(i))
     * where f(i) is the number of subarrays,
     * in which A[i] is the minimum.
     * left[i] + 1 equals to the number of subarray ending with A[i], and A[i] is single minimum.
     * right[i] + 1 equals to the number of subarray starting with A[i], and A[i] is first minimum.
     * f(i) = (left[i] + 1) * (right[i] + 1)
     * [3,1,2,4]
     * left + 1 = [1,2,1,1]
     * right + 1 = [1,3,2,1]
     * f = [1,6,2,1]
     * res = 3 * 1 + 1 * 6 + 2 * 2 + 4 * 1 = 17
     * @param o
     */

    public int sumSubarrayMins(int[] A) {
        int res = 0;
        int n = A.length;
        int mod = (int) 1e9 + 7;
        int[] left  = new int[n];
        int[] right  = new int[n];
        Stack<Integer[]> stack = new Stack<>();
        for (int i = 0; i< n; i++) {
            int count = 1;
            while(!stack.isEmpty() && stack.peek()[0] > A[i]) {
                count += stack.pop()[1];
            }
            stack.push(new Integer[] {A[i], count});
            left[i] = count;
        }
        stack.clear();
        for (int i = n-1; i>=0; i--) {
            int count = 1;
            while(!stack.isEmpty() && stack.peek()[0] >= A[i]) {
                count += stack.pop()[1];
            }
            stack.push(new Integer[] {A[i], count});
            right[i] = count;
        }
        for (int i = 0; i< n; i++) {
            res = (res + A[i] * left[i] * right[i]) % mod;
        }
        return res;
    }

    public static void foo(Object o) {
        System.out.println("Object impl");
    }
    public static void foo(String s) {
        System.out.println("String impl");
    }
    public static void foo(StringBuffer i){
        System.out.println("StringBuffer impl");
    }
}
