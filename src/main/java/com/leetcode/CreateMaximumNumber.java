package com.leetcode;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CreateMaximumNumber {

    public static void main(String[] args) {
        CreateMaximumNumber cmn = new CreateMaximumNumber();
        cmn.testMaximumNumber();
    }

    private void testMaximumNumber() {
        int[] nums1 = {6,7};
        int[] nums2 = {6,0, 4};
        int k = 5;
        int[] res = maxNumber(nums1, nums2, k);
        String resStr = IntStream.of(res).boxed().map(String::valueOf).collect(Collectors.joining(","));
        System.out.printf("Res: [%s]\n", resStr);
    }

    private int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int m = nums1.length, n = nums2.length;
        int[] max = new int[k];
        Arrays.fill(max, -1);
        for(int i = 0; i < k; i++) {
            if(i > m || (k-i) > n) {
                continue;
            }
            //System.out.printf("Nums1: %d elements, Nums2: %d elements\n", i, k-i);
            Deque<Integer> stack1 = getMaxStack(nums1, i);
            Deque<Integer> stack2 = getMaxStack(nums2, k-i);
            int[] currNum = merge(stack1, stack2);
            if(currentIsBigger(currNum, max)) {
                max = currNum;
            }
        }
        return max;
    }
    private boolean currentIsBigger(int[] curr, int[] nums) {
        for(int i = 0; i < curr.length; i++) {
            if(curr[i] > nums[i]) {
                return true;
            } else if(curr[i] < nums[i]) {
                return false;
            }
        }
        return false;
    }
    private int[] merge(Deque<Integer> stack1, Deque<Integer> stack2) {
        int m = stack1.size(), n = stack2.size();
        int[] res = new int[m+n];
        int i = 0;
        while(!stack1.isEmpty() && !stack2.isEmpty()) {
            if(stack1.peekLast() == stack2.peekLast()) {
                res[i] = stack1.removeLast();
                res[i] = stack2.removeLast();
                i += 2;
            } else if(stack1.peekLast() > stack2.peekLast()) {
                res[i] = stack1.removeLast();
                i += 1;
            } else {
                res[i] = stack2.removeLast();
                i += 1;
            }
        }
        while(!stack1.isEmpty()) {
            res[i] = stack1.removeLast();
            i += 1;
        }
        while(!stack2.isEmpty()) {
            res[i] = stack2.removeLast();
            i += 1;
        }
        return res;
    }
    private Deque<Integer> getMaxStack(int[] nums, int count) {
        int n = nums.length;
        int toDiscard = n - count;
        Deque<Integer> stack = new ArrayDeque<>();
        for(int num: nums) {
            while(!stack.isEmpty() && stack.peek() < num && toDiscard > 0) {
                stack.pop();
                toDiscard -= 1;
            }
            stack.push(num);
        }
        while(!stack.isEmpty() && toDiscard > 0) {
            stack.pop();
            toDiscard -= 1;
        }
        return stack;
    }
}
