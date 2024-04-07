package com.prep24;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        App app = new App();
        app.testCountStringWithPairs();
    }

    private void testCountStringWithPairs() {
        int count = countSubStrWithKPairs("010101", 2);
        System.out.printf("Count: %d\n", count);
    }

    private int countSubStrWithKPairs(String s, int k) {
        //int countPairs = countNumberOfPairs(s);
        int count = 0;
        for(int windowSize = k*2; windowSize <= s.length(); windowSize++) {
            for(int l = 0; l <= s.length() - windowSize; l++) {
                int r = l+windowSize-1;
                Map<Character, Integer> counter = new HashMap<>();
                for(int i = l; i <= r; i++) {
                    counter.merge(s.charAt(i), 1, Integer::sum);
                }
                if(atLeastKPairs(counter, k)) {
                    count += 1;
                }
            }
        }
        return count;
    }

    private boolean atLeastKPairs(Map<Character, Integer> counter, int k) {
        int cp = 0;
        int prev = 0;
        for(int freq: counter.values()) {
            if(prev > 0 && prev != freq ) {
                return false;
            }
            prev = freq;
            cp += 1;
        }
        return cp >= k;
    }

    private  int countNumberOfPairs(String s) {
        Map<Character,Integer> freq = new HashMap<>();
        for(char ch: s.toCharArray()) {
            freq.merge(ch, 1, Integer::sum);
        }
        int countPairs = 0;
        for(var t: freq.entrySet()) {
            if(t.getValue() > 1) {
                countPairs += 1;
            }
        }
        return countPairs;
    }
}
