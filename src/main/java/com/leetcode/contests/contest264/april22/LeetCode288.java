package com.leetcode.contests.contest264.april22;

import java.util.*;
import java.util.function.IntToDoubleFunction;
import java.util.stream.Collectors;

public class LeetCode288 {

    public static void main(String[] args) {
        LeetCode288 lc288 = new LeetCode288();
        //lc288.testBasiCalculator();
        //lc288.testMinRes();
        lc288.testSpiralMatrix();
    }

    private void testSpiralMatrix() {
        int n = 20;
        int[][] matrix = generateMatrix(n);
        for (int i = 0; i < n; i++) {
            System.out.printf("[%s]\n",
                    Arrays.stream(matrix[i]).mapToObj(e -> String.format("%1$3d", e)).collect(Collectors.joining(", ")));
        }
    }
//1,2 3,4  5,6
    public int[][] generateMatrix(int n) {
        int[][] grid = new int[n][n];
        int value = 1;
        int xStart = 0, xEnd = n-1, yStart = 0, yEnd = n-1;
        while(value <= n*n) {
            for(int y = yStart; y <= yEnd && value <= n*n; y++) {
                grid[xStart][y] = value;
                value += 1;
            }
            xStart += 1;
            for(int x = xStart; x <= xEnd && value <= n*n; x++) {
                grid[x][yEnd] = value;
                value += 1;
            }
            yEnd -= 1;
            for(int y = yEnd; y >= yStart && value <= n*n; y--) {
                grid[xEnd][y] = value;
                value += 1;
            }
            xEnd -= 1;
            for(int x = xEnd; x >= xStart  && value <= n*n; x--) {
                grid[x][yStart] = value;
                value += 1;
            }
            yStart += 1;
        }
        return grid;
    }

    private void testMinRes() {
        String expr = "999+999";
        minimizeResult(expr);

    }

    public void minimizeResult(String expression) {
        int len = expression.length();
        int index = expression.indexOf('+');
        int l = 0, r = len;
        int min = Integer.parseInt(expression.substring(0, index)) + Integer.parseInt(expression.substring(index+1));
        for(int i = 0; i < index; i++) {
            for(int j = index+2; j <= len; j++) {
                int l1 = i == 0 ? 1 : Integer.parseInt(expression.substring(0, i));
                int l2 = Integer.parseInt(expression.substring(i, index));

                int r1 = Integer.parseInt(expression.substring(index+1, j));
                int r2 = j == len ? 1: Integer.parseInt(expression.substring(j));
                System.out.printf("l1: %d, l2; %d, r1; %d, r2; %d\n", l1,l2, r1, r2);
                int curr = l1 *(l2+r1) * r2;
                System.out.printf("Min: %d, curr: %d\n", min, curr);
                if(curr < min) {
                    System.out.printf("i: %d, j: %d\n", i, j);
                    min = curr;
                    l = i;
                    r = j;
                }
            }
        }
        System.out.printf("Min: %d, l: %d, r: %d\n", min, l, r);
        StringBuilder sb = new StringBuilder(expression);
        sb.insert(r, ')');
        sb.insert(l, '(');

        System.out.printf("%s\n", sb.toString());
    }
    //3 * 7 * 2 * 5 + => (curr '+'  prev '*'); (curr '+', prev '+'), (curr'*', prev '*')
    // 3* 2 + 5 + 6
    // val + 3 + 4 + 5 + 6 * 7
    private void testBasiCalculator() {
        String expr = "3+7*2+8*9";
        int value = evaluateExpr(expr);
        System.out.printf("Value: %d\n", value);
    }
    private int evaluateExpr(String expr) {
        int res = 0, ln = 0, cn = 0;
        char po = '+';
        expr = expr.trim();
        for(int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);
            if(Character.isWhitespace(ch)) {
                continue;
            }
            if(Character.isDigit(ch)) {
                cn = cn * 10 + Character.getNumericValue(ch);
            }
            if(!Character.isDigit(ch) || i == expr.length()-1) {
                if(po == '+') {
                    res += ln;
                    ln = cn;
                } else {
                    ln *= cn;
                }
                cn = 0;
                if(i < expr.length()-1) {
                    po = ch;
                }
            }
        }
        res += ln;
        return res;
    }
    public int calculate(String s) {
        int res = 0, ln = 0, cn = 0;
        char lo = '+';
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(Character.isWhitespace(ch)) {
                continue;
            }
            if(Character.isDigit(ch)) {
                cn =  cn*10 + Character.getNumericValue(ch);
            }
            if(!Character.isDigit(ch) || i == s.length()-1) {
                if(lo == '+') {
                    res += ln;
                    ln = cn;
                } else if(lo == '-') {
                    res += ln;
                    ln = -cn;
                } else if(lo == '*') {
                    ln = ln*cn;
                } else if (lo == '/') {
                    ln = ln/cn;
                }
                if(i < s.length()-1) {
                    lo = ch;
                }
                cn = 0;
            }
        }
        res += ln;
        return res;
    }
}
