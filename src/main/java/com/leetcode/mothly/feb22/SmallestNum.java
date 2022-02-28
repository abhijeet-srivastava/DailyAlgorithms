package com.leetcode.mothly.feb22;

public class SmallestNum {
    public static void main(String[] args) {
        SmallestNum sm = new SmallestNum();
        sm.testRemoveNum();
    }

    private void testRemoveNum() {
        String num = "1432219";
        String res = removeKdigits(num, 3);
        System.out.printf("Res: %s\n", res);
    }

    public String removeKdigits(String num, int k) {
        boolean[] candidates = new boolean[num.length()];
        int prev = Character.getNumericValue(num.charAt(0));
        for(int  i = 1; i < num.length(); i++) {
            int current = Character.getNumericValue(num.charAt(i));
            System.out.printf("Prev: %d Current: %d\n", prev, current);
            if(prev > current) {
                candidates[i-1] = true;
                System.out.printf("Removal candidate: %d\n", i-1);
            }
            prev = current;
        }
        char[] array = new char[num.length() - k];
        int index = 0;
        int removed = 0;
        for(int i = 0; i < num.length(); i++) {
            if(candidates[i] && removed < k) {
                removed += 1;
            } else {
                array[index++] = num.charAt(i);
            }
        }
        return String.valueOf(array);
    }
}
