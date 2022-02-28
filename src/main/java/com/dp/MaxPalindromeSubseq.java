package com.dp;

import java.util.*;
import java.util.stream.IntStream;

public class MaxPalindromeSubseq {

    public static void main(String[] args) {
        MaxPalindromeSubseq ms = new MaxPalindromeSubseq();
        ms.testLongestCommonSbStr();
        int[] DP = {};
        int m = IntStream.of(DP).max().getAsInt()   ;
    }

    private void testLongestCommonSbStr() {
        String s = "abcdaf";
        String t = "zbcdf";
        String lcs = longestCommonSubString(s, t);
        System.out.printf("LCS: %s\n", lcs);
    }

    public int longestCommonSubSeq(String X, String Y) {
        return lcs(X,Y, X.length()-1, Y.length()-1);
    }

    public int longestCommonSubSeqDP(String X, String Y) {
        int m = X.length();
        int n = Y.length();
        int[][] DP = new int[m+1][n+1];
        for(int i = 1; i <=m; i++) {
            for(int j = 1; j <= n; j++) {
                if(X.charAt(i-1) == Y.charAt(j-1)) {
                    DP[i][j] = 1 + DP[i-1][j-1];
                } else {
                    DP[i][j] = Math.max(DP[i-1][j], DP[i][j-1]);
                }
            }
        }
        return DP[m][n];
    }
    private int lcs(String X, String Y, int m, int n) {
        if(m < 0 || n < 0) {
            return 0;
        }
        if(X.charAt(m) == Y.charAt(n)) {
            return 1 + lcs(X, Y, m-1, n-1);
        }
        return Math.max(
                lcs(X, Y, m-1, n),
                lcs(X, Y, m, n-1)
        );
    }


    public int longestPalindromeSubseq(String s) {
        return lps(0, s.length()-1, s);
    }
    private int lps(int l, int r, String s) {
        if(l == r) {
            return 1;
        } else if(l > r) {
            return 0;
        }
        if(s.charAt(l) == s.charAt(r)) {
            return 2 + lps(l+1, r-1, s);
        }
        return Math.max(lps(l+1, r, s), lps(l, r-1, s));
    }
    public int longestPalindromeSubseqDp(String s) {
        /**
         * Let DP[i][j] = Max length of palindrom substring in string of size i, and starting from index j
         * start index = j; end Index = i+j-1
         * DP[i][j] = s.charAt(j) == s.charAt(i+j-1) ? 2 + DP[i-2][j+1] //Reduce len by 2, start form j+1
         *                                  : Math.max(
         *                                      DP[i-1][j+1]
         *                                      :DP[i-1][j]
         *                                  )
         * DP[1][i] = 1
         */
        int len = s.length();
        int[][] DP = new int[len+2][len+1];//for i-2 and j+1
        for(int i = 0; i < len;i++) {
            DP[1][i] = 1;
        }
        for(int i = 2; i <= len; i++) {
            for(int j = 0; j <= len-i; j++) {
                int endIndex = j + i -1;
                if(s.charAt(j) == s.charAt(endIndex)) {
                    DP[i][j] = 2 + DP[i-2][j+1];
                } else {
                    DP[i][j] = Math.max(DP[i-1][j], DP[i-1][j+1]);
                }
            }
        }
        return DP[len][0];
    }

    private String longestCommonSubString(String s, String t) {
        int m = s.length();
        int n = t.length();
        int[][] DP = new int[m][n];
        int lcs = 0;
        String res = null;
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                if(s.charAt(i) == t.charAt(j)) {
                    if(i == 0 || j == 0) {
                        DP[i][j] = 1;
                    } else {
                        DP[i][j] = DP[i-1][j-1] + 1;
                        if(DP[i][j] > lcs) {
                            lcs = DP[i][j];
                            res = s.substring(i-lcs+1, i+1);
                        }
                    }
                }
            }
        }
        return res;
    }
    private String lcs1(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] DP = new int[m+1][n+1];
        int maxLen = 0;
        int lastIndex = 0;
        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= n; j++) {
                if(str1.charAt(i-1) != str2.charAt(j-1)) {
                    continue;
                }
                DP[i][j] = 1 + DP[i-1][j-1];
                if(DP[i][j] > maxLen) {
                    maxLen = DP[i][j];
                    lastIndex = i;
                }
            }
        }
        return str1.substring(lastIndex-maxLen, lastIndex);
    }
    public int longestCommonSubpath(int n, int[][] paths) {
        int m = paths.length;
        return Arrays.stream(paths).reduce((p1, p2) -> commonPath(p1, p2)).get().length;
    }

    private int[] commonPath(int[] p1, int[] p2) {
        //Map<Long, List<Integer>> seen = new HashMap<>();
        //seen.computeIfAbsent(1l, (e) -> new ArrayList<>()).add(5);
        int m = p1.length;
        int n = p2.length;
        int max = 0;
        int endIndex = -1;
        int[][] DP = new int [m+1][n+1];
        for(int i = 1; i <= m; i++) {
            for(int j = 1; j <= n; j++) {
                if(p1[i-1] == p2[j-1]) {
                    DP[i][j] = 1 + DP[i-1][j-1];
                }
                if(DP[i][j] > max) {
                    max = DP[i][j];
                    endIndex = i;
                }
            }
        }
        int[] res = new int[max];
        int index = 0;
        for(int i = endIndex - max + 1; i <= endIndex; i++) {
            res[index++] = p1[i];
        }
        return res;
    }

}
