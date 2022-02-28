package com.dp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NextSmallerNumber {
    public static void main(String[] args) {
        NextSmallerNumber nsm = new NextSmallerNumber();
        nsm.testNextSmallestNum();
    }

    private void testNextSmallestNum() {
        String num = "9438957234785635408";
        String smallest = minInteger(num, 23);
        System.out.printf("Smallest: %s\n", smallest);
    }

    //https://leetcode.com/problems/minimum-possible-integer-after-at-most-k-adjacent-swaps-on-digits/discuss/720548/O(n-logn)-or-Detailed-Explanation
    public String minInteger(String num, int k) {
        int [] arr = new int[num.length()];
        for(int i = 0; i < num.length(); i++) {
            arr[i] = Character.getNumericValue(num.charAt(i));
        }
        int l = 0;
        while(l < num.length() && k > 0) {
            int min = l;
            for(int i = l; i < num.length() && i-l <= k; i++) {
                if(arr[i] < arr[min]) {
                    min = i;
                }
            }
            if(l != min) {
                int tmp = arr[min];
                for(int j = min; j-1 >= l; j--) {
                    arr[j] = arr[j-1];
                }
                arr[l] = tmp;
            }
            k -= (min-l);
            l+=1;
        }
        return IntStream.of(arr).mapToObj(String::valueOf).collect(Collectors.joining());
    }

    private void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
