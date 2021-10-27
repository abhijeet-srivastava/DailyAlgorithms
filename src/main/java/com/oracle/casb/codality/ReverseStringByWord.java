package com.oracle.casb.codality;

import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created By : abhijsri
 * Date  : 08/12/18
 **/
public class ReverseStringByWord {

    public static void main(String[] args) {
        ReverseStringByWord rsbw = new ReverseStringByWord();
        //rsbw.testString();
        rsbw.testSteppingNumber();
    }

    private void testSteppingNumber() {
        List<Integer> res = getAllNum(8972);
        System.out.println(res.stream().map(Objects::toString).collect(Collectors.joining(",")));
    }

    private void testString() {
        String original = "my java program";
        String reverse = reverseString(original);
        System.out.println("Reversed String: " + reverse);
    }

    private String reverseString(String original) {
        StringBuilder res = new StringBuilder();
        int start = 0;
        int end = 0;
        int i = 0;
        while (i < original.length()) {
            while (i < original.length() &&  Character.isSpaceChar(original.charAt(i))) {
                i += 1;
            }
            start = i;
            while (i < original.length() && !Character.isSpaceChar(original.charAt(i))) {
                i += 1;
            }
            end = i-1;
            res.append(reverseSubString(original, start, end));
            res.append(" ");
        }
        return res.toString();
    }

    private char[] reverseSubString(String original, int start, int end) {
        int len = end - start + 1;
        char[] array = new char[end - start + 1];
        int stIndex = 0;
        int endIndex = len - 1;
        while (stIndex <= endIndex) {
            array[stIndex++] = original.charAt(end--);
            array[endIndex--] = original.charAt(start++);
        }
        return array;
    }
    private List<Integer> getAllNum(int num) {
        List<Integer> res = new LinkedList<>();
        int d = getNumDigits(num);
        List<Integer> current = Lists.asList(1, new Integer[]{2, 3, 4, 5, 6, 7, 8, 9});
        for (int i = 2; i < d; i++) {
            List<Integer> listOfDigits = createListOfDigits( current);
            res.addAll(current);
            current = listOfDigits;
        }
        res.addAll(current);
        res.addAll(getAllInCurrent(num, current));
        return res;
    }

    private Collection<? extends Integer> getAllInCurrent(int number, List<Integer> current) {
        List<Integer> res = new LinkedList<>();
        for (int num : current) {
            int lastDigit = num % 10;
            if (lastDigit > 0) {
                int step1 = num * 10 + lastDigit - 1;
                if (step1 < number)
                    res.add(step1);
            }
            if (lastDigit < 9) {
                int step1 = num * 10 + lastDigit + 1;
                if (step1 < number)
                    res.add(step1);
            }
        }
        return res;
    }

    private List<Integer> createListOfDigits(List<Integer> current) {
        List<Integer> res = new LinkedList<>();
        for (int num : current) {
            int lastDigit = num % 10;
            if (lastDigit > 0) {
                res.add(num * 10 + lastDigit - 1);
            }
            if (lastDigit < 9) {
                res.add(num * 10 + lastDigit + 1);
            }
        }
        return res;
    }

    private int getNumDigits(int num) {
        int count = 0;
        while(num > 0) {
            num /= 10;
            count += 1;
        }
        return count;
    }
}
