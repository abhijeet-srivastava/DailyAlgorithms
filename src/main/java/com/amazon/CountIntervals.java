package com.amazon;

import java.util.*;

public class CountIntervals {
    TreeMap<Integer, Integer> map;
    int count = 0;
    public CountIntervals() {
        map = new TreeMap<>();
    }

    public void add(int left, int right) {
        Map.Entry<Integer, Integer> entry = map.floorEntry(left);
        if(entry == null || entry.getValue() < left){
            entry = map.higherEntry(left);
        }
        while(entry != null && entry.getKey() <= right){
            left = Math.min(left, entry.getKey());
            right = Math.max(right, entry.getValue());
            map.remove(entry.getKey());
            count -= (entry.getValue() - entry.getKey() + 1);
            entry = map.higherEntry(left);
        }
        map.put(left, right);
        count += (right-left+1);
    }

    public int count() {
        return count;
    }
}
