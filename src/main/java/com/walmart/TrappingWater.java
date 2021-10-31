package com.walmart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class TrappingWater {

    public static void main(String[] args) {
        TrappingWater tw = new TrappingWater();
        tw.testByStack();

    }

    private void testByStack() {
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        int water = trap(height);
        System.out.printf("Water: %d\n", water);
    }

    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int result = 0;
        for(int i = 0; i < height.length; i++) {
            while(!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int left = stack.pop();
                if(stack.isEmpty()) {
                    break;
                }
                int distance = i - stack.peek() - 1;
                int bounded = Math.min(height[i], height[stack.peek()]) - height[left];
                result += (distance * bounded);
            }
            stack.push(i);
        }
        return result;
    }

    public int trap2Pointer(int[] height) {
        int result = 0;
        int left = 0, right = height.length-1;
        int leftMax = 0, rightMax = 0;
        while(left < right) {
            if(height[left] < height[right]) {
                if(height[left] > leftMax) {
                    leftMax = height[left];
                } else {
                    result += (leftMax - height[left]);
                }
                left += 1;
            } else {
                if(height[right] > rightMax) {
                    rightMax = height[right];
                } else {
                    result += (rightMax - height[right]);
                }
                right -= 1;
            }
        }
        return result;
    }
    public int trapDP(int[] height) {
        int n = height.length;
        int[] left = new int[n+1];
        int[] right = new int[n+1];
        int fromLeft = 0;
        int fromRight = 0;
        for(int i = 0; i < n ; i++) {
            fromLeft = Math.max(height[i], fromLeft);
            left[i] = fromLeft;
            fromRight = Math.max(height[n-i-1], fromRight);
            right[n-i-1] = fromRight;
        }
        int total = 0;
        for(int i = 0; i < n;i++) {

            //System.out.printf("Index : %d, left: %d, right ; %d\n", i, left[i], right[i]);
            int h = Math.min(left[i], right[i]) - height[i];
            total += h;
        }
        return total;
    }
    String S;
    public int search(int L, int a, long modulus, int n, int[] nums) {
        // Compute the hash of string S[:L]
        long h = 0;
        for (int i = 0; i < L; ++i) {
            h = (h * a + nums[i]) % modulus;
        }

        // Store the already seen hash values for substrings of length L.
        HashMap<Long, List<Integer>> seen = new HashMap<Long, List<Integer>>();

        // Initialize the hashmap with the substring starting at index 0.
        seen.putIfAbsent(h, new ArrayList<Integer>());
        seen.get(h).add(0);

        // Const value to be used often : a**L % modulus
        long aL = 1;
        for (int i = 1; i <= L; ++i) {
            aL = (aL * a) % modulus;
        }

        for (int start = 1; start < n - L + 1; ++start) {
            // Compute rolling hash in O(1) time
            h = (h * a - nums[start - 1] * aL % modulus + modulus) % modulus;
            h = (h + nums[start + L - 1]) % modulus;
            List<Integer> hits = seen.get(h);
            if (hits != null) {
                // Check if the current substring matches any of
                // the previous substrings with hash h.
                String cur = S.substring(start, start + L);
                for (Integer i : hits) {
                    String candidate = S.substring(i, i + L);
                    if (candidate.equals(cur)) {
                        return i;
                    }
                }
            }
            // Add the current substring's hashvalue and starting index to seen.
            seen.putIfAbsent(h, new ArrayList<Integer>());
            seen.get(h).add(start);
        }
        return -1;
    }

    public String longestDupSubstring(String s) {
        S = s;
        int n = S.length();

        // Convert string to array of integers to implement constant time slice
        int[] nums = new int[n];
        for (int i = 0; i < n; ++i) {
            nums[i] = (int)S.charAt(i) - (int)'a';
        }

        // Base value for the rolling hash function
        int a = 26;

        // modulus value for the rolling hash function to avoid overflow
        int modulus = 1_000_000_007;

        // Binary search, L = repeating string length
        int left = 1, right = n;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (search(mid, a, modulus, n, nums) != -1) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        int start = search(left - 1, a, modulus, n, nums);
        return S.substring(start, start + left - 1);
    }
}
