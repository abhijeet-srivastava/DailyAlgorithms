package com.leetcode.mothly.june25;

import java.util.ArrayDeque;
import java.util.Deque;

public class LeetCodeDaily {


    public static void main(String[] args) {
        LeetCodeDaily lcd = new LeetCodeDaily();
        //lcd.validateRobotPrint();
        lcd.validateNumParity();
    }

    private void validateNumParity() {
        int[] nums = {1,-1,1,1,-1,-1,-1,1,-1};
        boolean res = canMakeEqual(nums, 4);
        System.out.printf("Res: %b\n", res);
    }

    private void validateRobotPrint() {
        String input = "vzhofnpo";
        String res = robotWithString(input);
        System.out.printf("Res: %s\n", res);
    }

    public String robotWithString(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            stack.push(ch);
            while(!stack.isEmpty() &&
                    //isSmallest(stack.peek(), frequencies)) {
                    isSmallest(stack.peek(), i, s)) {
                sb.append(stack.pop());
            }
        }
        while(!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    private boolean isSmallest(Character ch, int i, String s) {
        for(int j = i+1; j < s.length(); j++) {
            if(s.charAt(j) < ch) {
                return false;
            }
        }
        return true;
    }

    private boolean isSmallest(char ch, char[] frequencies) {
        for(char start = ch; start >= 'a'; start-=1) {
            if(frequencies[start - 'a'] > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean canMakeEqual(int[] nums, int k) {
        return canMakeEqual(1, nums.clone(), k) || canMakeEqual(-1, nums, k);
    }
    private boolean canMakeEqual(int val, int[] nums, int k) {
        int idx = 0;
        while(idx < nums.length && nums[idx] == val) {
            idx += 1;
        }
        for(;idx < nums.length-1 && k > 0; idx++) {
            if(nums[idx] != val) {
                nums[idx] *= (-1);
                nums[idx+1] *= (-1);
                k -= 1;
            }
        }
        while(idx < nums.length) {
            if(nums[idx] != val) {
                return false;
            }
            idx += 1;
        }
        return true;
    }
}
