package com.leetcode.mothly.my25;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class LeetCodeDaily {

    public static void main(String[] args) {
        LeetCodeDaily lcd = new LeetCodeDaily();
        lcd.test16May();
    }

    private void test16May() {
        String[] words = {"bab","dab","cab"};
        int[] groups = {1,2,2};
        List<String> res = getWordsInLongestSubsequence(words, groups);
        res.stream().forEach(System.out::println);
    }


    public List<String> getWordsInLongestSubsequence(String[] words, int[] groups) {
        int len = words.length;
        WordGroup[] DP = new WordGroup[len];
        for(int i = 0; i < len; i++) {
            DP[i] = new WordGroup(words[i], groups[i], 1, -1);
        }
        int maxIdx = 0;
        for(int i = 1; i < len; i++) {
            int maxLenIdx = 1;
            for(int j = i-1; j >= 0; j--) {
                if(DP[i].group != DP[j].group
                        && hammingDistance(DP[i].word, DP[j].word) == 1
                        && DP[i].len < (1+DP[j].len)) {
                    DP[i] = new WordGroup(words[i], groups[i], 1+DP[j].len, j);
                }
            }
            if(DP[i].len > DP[maxIdx].len) {
                maxIdx = i;
            }
        }
        List<String> res = new LinkedList<>();
        while(maxIdx >= 0) {
            res.add(0, DP[maxIdx].word);
            maxIdx =  DP[maxIdx].prev;
        }
        return res;
    }

    private int hammingDistance(String w1, String w2) {
        if(w1.length() != w2.length()) {
            return 2;
        }
        int dist = 0;
        for(int idx = 0; idx < w1.length();idx++) {
            if(w1.charAt(idx) != w2.charAt(idx)) {
                dist += 1;
            }
        }
        return dist;
    }
    private class WordGroup {
        String word;
        int group;
        int len;
        int prev;
        public WordGroup(String word, int group, int len, int prev) {
            this.word = word;
            this.group = group;
            this.len = len;
            this.prev = prev;
        }
    }

    public int minTimeToReach(int[][] moveTime) {
        int m = moveTime.length, n = moveTime[0].length;
        int[][] mttr = new int[m][n];
        for(int[] row: mttr) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        mttr[0][0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> Integer.compare(a[0], b[0]));
        int[][] dirs = {{0,1}, {1, 0}, {0,-1}, {-1,0}};
        pq.offer(new int[]{0, 0, 0, 1});
        while(!pq.isEmpty()) {
            int[] curr = pq.poll();
            int ct = curr[0], cx = curr[1], cy = curr[2], cd = curr[3];
            if(cx == m-1 && cy == n-1) {
                return ct;
            }
            int nd = (cd == 1) ? 2:1;
            for(int[] dir: dirs) {
                int nx = cx + dir[0], ny = cy  + dir[1];
                if(nx < 0 || nx >= m || ny < 0 || ny >= n) {
                    continue;
                }
                int ttr = Math.max(ct, moveTime[nx][ny]) + cd;
                if(ttr < mttr[nx][ny]) {
                    mttr[nx][ny] = ttr;
                    pq.offer(new int[]{ttr, nx, ny, nd});
                }
            }
        }
        return -1;
    }
}
