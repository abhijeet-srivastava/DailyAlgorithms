package com.agoda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Application {
    public static void main(String[] args) {
        Application app = new Application();
        app.validateCountPairs();
    }

    private void validateCountPairs() {
        int[] arr = {2,18, 11,7, 14, 51, 54,7,14,7,14, 11, 14};
        int count = findPairCount(arr);
        System.out.println("Count: "+ count);
        int count1 = findPairCount1(arr);
        System.out.println("Count1: "+ count1);
    }
    private int findPairCount1(int[] arr) {
        Arrays.sort(arr);
        Map<Integer, Integer> counter = new HashMap<>();
        int minDiff = Integer.MAX_VALUE;
        for(int i = 0; i < arr.length; i++) {
            counter.merge(arr[i], 1, Integer::sum);
            if(i > 0 && arr[i] != arr[i-1])  {
                minDiff = Math.min(minDiff, arr[i]-arr[i-1]);
            }
        }
        int res = 0;
        int prev = arr[0];
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == prev) {
                continue;
            }
            int diff = arr[i] - prev;
            if(diff == minDiff) {
                res += (counter.get(prev)*counter.get(arr[i]));
            }
            prev = arr[i];
        }
        return res;
    }
    private int findPairCount(int[] arr) {
        TreeMap<Integer, Integer> tm = new TreeMap<>();
        for(int num: arr) {
            tm.merge(num, 1, Integer::sum);
        }
        Map<Integer, Integer> diffPairs = new HashMap<>();
        Map.Entry<Integer, Integer> prevEntry = null;
        int minDiff = Integer.MAX_VALUE;
        for(var entry: tm.entrySet()) {
            if(prevEntry == null) {
                prevEntry = entry;
                continue;
            }
            int diff = entry.getKey() - prevEntry.getKey();
            int currPairCount = entry.getValue()* prevEntry.getValue();
            int currCount = diffPairs.getOrDefault(diff, 0);
            diffPairs.put(diff, currCount+currPairCount);
            minDiff = Math.min(minDiff, diff);
            prevEntry = entry;
        }
        return diffPairs.get(minDiff);
    }
}
