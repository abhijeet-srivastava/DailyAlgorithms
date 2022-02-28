package com.meta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LongestCommonSubsequence {

    public static void main(String[] args) {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence();
        lcs.testLcs();
    }

    private void testLcs() {
        //String lcs = shortestCommonSupersequence("abac", "cab");
        String lcs = longestCommonSubsequence("AGGTAB", "GXTXAYB");
        System.out.printf("LCS: %s\n", lcs);
    }

    public String shortestCommonSupersequence(String str1, String str2) {
        //str1 = "abac", str2 = "cab"
        int[][]  DP = new int[str1.length()+1][str2.length()+1];
        List<Character> list = new ArrayList<>();
        for(int i = 1; i <= str1.length(); i++) {
            for(int j = 1; j <= str2.length(); j++) {
                if(str1.charAt(i-1) == str2.charAt(j-1)) {
                    DP[i][j]= DP[i-1][j-1] + 1;
                } else if(DP[i-1][j] > DP[i][j-1]) {
                    DP[i][j] = DP[i-1][j];
                } else {
                    DP[i][j] = DP[i][j-1];
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        int i = str1.length();
        int j = str2.length();
        while(i > 0 || j > 0) {
            if(i <= 0) {
                sb.insert(0, str2.charAt(j-1));
                j -= 1;
                continue;
            } else if(j <= 0) {
                sb.insert(0, str1.charAt(i-1));
                i -= 1;
                continue;
            }
            if(str1.charAt(i-1) == str2.charAt(j-1)) {
                sb.insert(0, str1.charAt(i-1));
                i -= 1;
                j -= 1;
            } else if(DP[i-1][j] > DP[i][j-1]) {
                sb.insert(0, str1.charAt(i-1));
                i -= 1;
            } else {
                sb.insert(0, str2.charAt(j-1));
                j -= 1;
            }
        }
        return sb.toString();
    }

    private String longestCommonSubsequence(String str1, String str2) {
        int[][] DP = new int[str1.length()+1][str2.length()+1];
        for(int i = 1; i <= str1.length(); i++) {
            for(int j = 1; j < str2.length(); j++) {
                if(str1.charAt(i-1) == str2.charAt(j-1)) {
                    DP[i][j] = DP[i-1][j-1] + 1;
                } else {
                    DP[i][j] = Math.max(DP[i-1][j], DP[i][j-1]);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        int i = str1.length();
        int j = str2.length();
        while(i > 0 &&  j > 0) {
            if(str1.charAt(i-1) == str2.charAt(j-1)) {
                sb.insert(0, str1.charAt(i-1));
                i -= 1;
                j -= 1;
            } else if(DP[i-1][j] >= DP[i][j-1]) {
                i -= 1;
            } else {
                j -= 1;
            }
        }
        return sb.toString();
    }
}
