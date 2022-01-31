package com.meta;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MonotonicStack {

    public static void main(String[] args) {
        MonotonicStack ms = new MonotonicStack();
        ms.testNextGreater();
    }

    private void testNextGreater() {
        int[] arr = {1, 2, 1};
        int[] res = nextGreaterElements(arr);
        System.out.printf("Res: [%s]\n", IntStream.of(res).mapToObj(String::valueOf).collect(Collectors.joining(", ")));
    }

    public int[] nextGreaterElements(int[] nums) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] tmp = nums.clone();
        for(int i = nums.length-1; i >= 0; i--) {
            while(!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
                stack.pop();
            }
            tmp[i] = stack.isEmpty() ? -1 : nums[stack.peek()];
            stack.push(i);
        }
        for(int i = nums.length-1; i >= 0; i--) {
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[i]) {
                stack.pop();
            }
            tmp[i] = stack.isEmpty() ? -1 : nums[stack.peek()];
            stack.push(i);
        }
        return tmp;
    }
}
