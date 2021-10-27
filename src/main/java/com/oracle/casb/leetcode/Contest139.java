package com.oracle.casb.leetcode;

import java.util.Arrays;

public class Contest139 {
    public static void main(String[] args) {
        Contest139 ct =new Contest139();
        ct.testDivides();
    }

    private void testDivides() {
        String str = "ABCABC";
        String fact = "ABC";
        System.out.printf("GCD : %s\n", gcdOfStrings(str, fact));
    }

    public String gcdOfStrings(String str1, String str2) {
        int len1 = str1.length();
        int len2 =str2.length();
        int gcd = gcd(len1, len2);
        String str = str1.substring(0, gcd);
        String res = "";
        if (divides(str1, str) && divides(str2, str)) {
            res = str;
        }
        return res;
    }


    private boolean divides(String str, String factor) {
        if (str.equals(factor)) {
            return true;
        } else if (factor.length() > str.length()) {
            return false;
        }
        return str.startsWith(factor) && divides(str.substring(factor.length()), factor);
    }
    private int gcd(int num1, int num2) {
        while (num1 != num2) {
            if (num1 > num2) {
                num1 -= num2;
            } else {
                num2 -= num1;
            }
        }
        return num1;
    }
    public int maxEqualRowsAfterFlips(int[][] matrix) {
        int ans = 0;
        int m = matrix.length, n = matrix[0].length;
        int[] flip = new int[n];
        for(int i = 0; i < m; i++) {
            int cnt = 0;
            for(int j = 0; j < n; j++) flip[j] = 1 - matrix[i][j];
            for(int k = 0; k < m; k++) {
                if(Arrays.equals(matrix[k], matrix[i]) || Arrays.equals(matrix[k], flip)) cnt++;
            }
            ans = Math.max(ans, cnt);
        }
        return ans;
    }
}
