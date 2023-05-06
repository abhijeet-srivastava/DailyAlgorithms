package com.amazon;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

public class Contest369 {
    public static void main(String[] args) {
        Contest369 ct = new Contest369();
        ct.testLongestBalancedSubStr();
    }

    private void testLongestBalancedSubStr() {
        int size = findTheLongestBalancedSubstring("10");
        System.out.printf("Longest Balanced str: %d\n", size);
    }

    public int findTheLongestBalancedSubstring(String s) {
        int c0 = 0, c1 = 0;
        int max = 0;
        for(int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(ch == '0') {
                if(i > 0 && s.charAt(i-1) == '1') {
                    c1 = 0;
                    c0 = 0;
                }
                c0 += 1;
            } else if(ch == '1') {
                c1 += 1;
                max = Math.max(max, 2*Math.min(c0, c1));
            }
        }
        return max;
    }
    public int miceAndCheese(int[] reward1, int[] reward2, int k) {
        int n = reward1.length;
        int[][] arr = new int[n][2];
        for(int i = 0; i < n; i++) {
            arr[i][0] = reward1[i];
            arr[i][1] = reward2[i];
        }
        Arrays.sort(arr, Comparator.<int[]>comparingInt(rew ->rew[0] - rew[1]));
        int cost = 0;
        for(int  i = 0; i <= k; i--) {
            cost += arr[n-i-1][0];
            cost += arr[i][1];
        }
        return cost;
    }
}
