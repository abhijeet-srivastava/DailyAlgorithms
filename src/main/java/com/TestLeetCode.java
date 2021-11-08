package com;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestLeetCode {

    public static void main(String[] args) {
        TestLeetCode tlc = new TestLeetCode();
        tlc.testRemove();
    }

    private void testRemove() {
        int[] nums = {3,2,2,3};
        //String str  = IntStream.of(nums).mapToObj(String::valueOf).collect(Collectors.joining("','", "'", "'"));
        //System.out.println(str);
        int len = removeElement(nums, 3);
        System.out.printf("Res: %d\n", len);
    }

    public int removeElement(int[] nums, int val) {
        //String[] wordlist = {};
        //List<String> words = Stream.<String>of(wordlist).collect(Collectors.toList());
        int i = 0;
        int j = 0;
        while(j < nums.length) {
            while(j < nums.length && nums[j] == val) {
                j += 1;
            }
            if(j < nums.length) {
                nums[i] = nums[j];
                i += 1;
                j += 1;
            }
        }
        return i;
    }
}
