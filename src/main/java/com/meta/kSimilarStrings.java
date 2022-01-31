package com.meta;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class kSimilarStrings {

    public int kSimilarity(String s1, String s2) {
        return kSimilarityDFS(s1, s2);
        //eturn kSimilarityBFS(s1, s2);
    }

    private int kSimilarityDFS(String s1, String s2) {
        Map<String, Integer> DP = new HashMap<>();
        DP.put(s1, 0);
        return dfs(0, s1, s2, DP);
    }

    private int dfs(int pos, String s1, String s2, Map<String, Integer> DP) {
        if(DP.containsKey(s2)) {
            return DP.get(s2);
        }
            while(pos < s1.length() && s1.charAt(pos) == s2.charAt(pos)) {
                pos += 1;
            }
            int minPerm = pos == s1.length() ? 0 : Integer.MAX_VALUE;
        char[] s2Char = s2.toCharArray();
        for(int i = pos; i < s1.length(); i++) {
            if(s1.charAt(pos) != s2.charAt(i)
                    || s1.charAt(i) == s2.charAt(i)) {
                continue;
            }
            swap(s2Char, i, pos);
            minPerm = Math.min(minPerm, dfs(i+1, s1, String.valueOf(s2Char), DP)+1);
            swap(s2Char, i, pos);
        }
        DP.put(s2, minPerm);
        return minPerm;
    }

    private void swap(char[] s2Char, int i, int pos) {
        char ch = s2Char[i];
        s2Char[i] = s2Char[pos];
        s2Char[pos] = ch;
    }
    public long maxRunTime(int n, int[] batteries) {
        long hi = 0;
        for(int bat : batteries) {
            hi += bat;
        }
        hi /= n;
        long lo = 0;
        long res = lo;
        while(lo <= hi) {
            long mid = lo + ((hi-lo) >> 1);
            if(canRun(mid, n, batteries)) {
                res = mid;
                lo = mid+1;
            } else {
                hi = mid-1;
            }
        }
        return res;
    }

    private boolean canRun(long mid, int n, int[] batteries) {
        long batSum = 0l;
        long totalReq = mid*n;
        for(int bat: batteries) {
            batSum += Math.min(mid, bat);
        }
        return batSum >= totalReq;
    }
    }
