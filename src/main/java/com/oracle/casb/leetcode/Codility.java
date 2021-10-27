package com.oracle.casb.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Codility {

    public static void main(String[] args) {
        Codility ct = new Codility();
        //ct.testDemo();
        ct.testBinary();
    }

    private void testBinary() {

        int n = 955;
        int[] array = getBinary(n);
        IntStream.rangeClosed(1, array.length).mapToObj(i -> array[array.length - i]).toArray();
        System.out.printf("%s\n", Arrays.stream(array).boxed().map(String::valueOf).collect(Collectors.joining(",")));
        int[] dm = toIntarray(Integer.toBinaryString(n).toCharArray());
        System.out.printf("%s\n", Arrays.stream(dm).boxed().map(String::valueOf).collect(Collectors.joining(",")));
    }

    private int[] getBinary(int n) {
        int[] d = new int[30];
        int l = 0;
        while (n > 0) {
            d[l] = n % 2;
            n /= 2;
            l++;
        }
        for (int i = 0; i <= l; i++,l--) {
            int tmp = d[i];
            d[i] = d[l-1];
            d[l-1] = tmp;
        }
        System.out.printf("Value of l : %d\n", l);
        return d;
    }
    private void testDemo() {
//        int foo = Integer.parseInt("1001100110", 2);
//        System.out.printf("%d\n", foo);
        int foo = 614;
        int[] d = toIntarray(Integer.toBinaryString(foo).toCharArray());
        //int[] d = {1,0,0,1,1,0,0,1,1,0};
       // Arrays.stream(d).boxed().map(String::valueOf).collect(Collectors.joining(","));
        System.out.printf("%s\n", Arrays.stream(d).boxed().map(String::valueOf).collect(Collectors.joining(",")));
    }

    private int[] toIntarray(char[] charArray) {
        int[] d = new int[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            d[i] = Character.getNumericValue(charArray[i]);
        }
        return d;
    }

    public int solution(int N) {
        // write your code in Java SE 8
        char[] d = Integer.toBinaryString(N).toCharArray();
        int start = 0;
        int end = 0;
        int max = 0;
        for (int i = 0; i < d.length; i++) {
            if(d[i] == '0') {
                start = i;
                while (i < d.length && d[i] == '0') {
                    i += 1;
                }
                if (i < d.length) {
                    max = Integer.max(max, i-start);
                }
            }
        }
        return max;
    }
}
