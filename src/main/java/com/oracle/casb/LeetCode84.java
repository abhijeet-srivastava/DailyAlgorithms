package com.oracle.casb;

import java.util.Arrays;

/**
 * Created By : abhijsri
 * Date  : 15/05/18
 **/
public class LeetCode84 {

    public static void main(String[] args) {
        LeetCode84 lc = new LeetCode84();
        //System.out.println(lc.reverseWords("  the        sky   is    blue      "));
        System.out.println(lc.reverseString("    the       sky is blue    "));
        //lc.strStr("abc", "abcd");
        /*String res = lc.findReplaceString("vmokgggqzp", new int[] { 3, 5, 1 }, new String[] { "kg", "ggq", "mo" },
                new String[] { "s", "so", "bfr" });
        System.out.println(res);*/
    }

    private String findReplaceString(String S, int[] indexes, String[] sources, String[] targets) {
        char[] arr = S.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            int ind = getIndexFromIndexes(i, indexes);
            //if (index < indexes.length && i == indexes[index]) {
            if (ind >= 0 && ind < indexes.length) {
                String source = sources[ind];
                if (isPresent(arr, i, source.toCharArray())) {
                    sb.append(targets[ind]);
                    i += source.length() - 1;
                } else {
                    sb.append(arr[i]);
                }
            } else {
                sb.append(arr[i]);
            }
        }
        return sb.toString();
    }

    private int getIndexFromIndexes(int i, int[] indexes) {
        int ind = -1;
        for (ind = 0; ind < indexes.length; ind++) {
            if (i == indexes[ind]) {
                break;
            }
        }
        return ind;
    }

    private boolean isPresent(char[] arr, int ind, char[] targetArr) {
        boolean present = true;
        for (int i = 0; i < targetArr.length; i++) {
            if (arr[ind + i] != targetArr[i]) {
                present = false;
                break;
            }
        }
        return present;
    }

    private int strStr(String haystack, String needle) {
        for (int i = 0; ; i++) {
            for (int j = 0; ; j++) {
                if (j == needle.length())
                    return i;
                if (i + j == haystack.length())
                    return -1;
                if (needle.charAt(j) != haystack.charAt(i + j))
                    break;
            }
        }
    }

    private String reverseString(String word) {
        int len = word.length();
        char[] array = word.toCharArray();
        reverse(array, 0, len-1);
        for(int i = 0, start = 0; i < len; i++) {
            if (i == len-1) {
                reverse(array, start, i);
            } else if (Character.isSpaceChar(array[i])) {
                reverse(array, start, i-1);
                start = i+1;
            }
        }
        return new String(array);
    }
    private void reverse(char[] array, int start, int end) {
        while (start < end) {
            char tmp = array[start];
            array[start] = array[end];
            array[end] = tmp;
            start += 1;
            end -= 1;
        }
        //return array;
    }

    private String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        int end = s.length();
        //int start = end;
        for (int start = s.length() - 1; start >= 0; start--) {
            if (Character.isSpaceChar(s.charAt(start))) {
                end = start;
            } else {
                if (start == 0 || Character.isSpaceChar(s.charAt(start - 1))) {
                    if (sb.length() != 0) {
                        sb.append(' ');
                    }
                    sb.append(getSubStr(s, start, end));
                }
            }

        }
        return sb.toString();
    }

    private String getSubStr(String s, int start, int end) {
        char[] subStr = new char[end - start];
        for (int i = start; i < end; i++) {
            subStr[i-start] = s.charAt(i);
        }
        return new String(subStr);
    }
}
