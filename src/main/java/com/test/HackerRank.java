package com.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class HackerRank {

    public static void main(String[] args) {
        HackerRank hr = new HackerRank();
        //hr.testSquare();
        hr.testIsValid();
    }

    private void testIsValid() {
        String word = "Ya$";
        System.out.printf("%s is valid: %b\n", word, isValid(word));
    }

    public boolean isValid(String word) {
        if(word.length() < 3) {
            return false;
        }
        boolean vowel = false, consonant = false, digit = false;
        for(char ch : word.toCharArray()) {
            if(isVowel(ch)) {
                vowel = true;
                System.out.printf("%c is Vowel\n", ch);
            } else if(isConsonant(ch)){
                consonant = true;
                System.out.printf("%c is consonant\n", ch);
            } else if(isDigit(ch)){
                System.out.printf("%c is digit\n", ch);
                continue;
            } else {
                System.out.printf("%c is invalid\n", ch);
                return false;
            }
        }
        return consonant && vowel;
    }
    private boolean isConsonant(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }
    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }
    private boolean isVowel(char ch) {
        return ch == 'a' || ch == 'A'
                || ch == 'e' || ch == 'E'
                || ch == 'i' || ch == 'I'
                || ch == 'o' || ch == 'O'
                || ch == 'u' || ch == 'U';
    }

    private void testSquare() {
        List<String> grid = Arrays.asList("GGGGGGG", "BGBBBBG", "BGBBBBG", "GGGGGGG", "GGGGGGG", "BGBBBBG" );
        int sq = twoPluses(grid);
        System.out.printf("Square: %d\n",sq);
    }
    public  int twoPluses(List<String> grid) {
        // Write your code here
        int m = grid.size();
        if(m == 0) {
            return 0;
        }
        int n = grid.get(0).length();
        int[][] DP = new int[m][n];
        for(int r = 0; r < m; r++) {
            int count = 0;
            for(int c = 0; c < n; c++) {
                if(grid.get(r).charAt(c) == 'G') {
                    count += 1;
                    DP[r][c] = count;
                } else {
                    count = 0;
                }
            }
            count = 0;
            for(int c = n-1; c >= 0; c--) {
                if(grid.get(r).charAt(c) == 'G') {
                    count += 1;
                    DP[r][c] = Math.min(DP[r][c], count);
                } else {
                    count = 0;
                }
            }
        }
        for(int c = 0; c < n; c++) {
            int count = 0;
            for(int r = 0; r < m; r++) {
                if(grid.get(r).charAt(c) == 'G') {
                    count += 1;
                    DP[r][c] = Math.min(DP[r][c], count);
                } else {
                    count = 0;
                }
            }
            count = 0;
            for(int r = m-1; r >= 0; r--) {
                if(grid.get(r).charAt(c) == 'G') {
                    count += 1;
                    DP[r][c] = Math.min(DP[r][c], count);
                } else {
                    count = 0;
                }
                /*if(DP[r][c] > max) {
                    secMax = max;
                    max = DP[r][c];
                } else if(DP[r][c] > secMax) {
                    secMax = DP[r][c];
                }*/
            }
        }
        int[][] arr = new int[m*n][2];
        for(int i = 0; i < m; i++) {
            for(int j = 0; j < n; j++) {
                arr[i*n+j] = new int[]{DP[i][j], i*n+j};
            }
        }
        Arrays.sort(arr, (a, b) -> b[0]-a[0]);
        int maxProd = 0;
        for(int i = 0; i < arr.length-1; i++) {
            for(int j = i+1; j < arr.length; j++) {
                int[] f = arr[i], s = arr[j];
                int fr = f[1]/n, fc = f[1]%n;
                int sr = s[1]/n, sc = s[1]%n;
                int total = f[0] + f[1];
                if(!areOverLapping(fr, fc, sr, sc)) {
                    maxProd = (4*f[0]-3) * (4*s[0]-3);
                    break;
                }
            }
        }
        //return (4*max-3)*(4*secMax-3);
        return maxProd;

    }

    private boolean areOverLapping(int fr, int fc, int sr, int sc) {
        //TODO: Implement
        return false;
    }
}
