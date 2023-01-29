package com.amazon;


import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class LFUCache {
    private Map<Integer,int[]> cache;
    private Map<Integer, LinkedHashSet<Integer>> frequencies;
    private int minf;
    private int capacity;

    private void insert(int key, int freq, int val) {
        int[] freqVal = {freq, val};
        this.cache.put(key, freqVal);
        this.frequencies.computeIfAbsent(key, e -> new LinkedHashSet<>()).add(key);
    }

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minf = 0;
        this.cache = new HashMap<>();
        this.frequencies = new HashMap<>();
    }

    private int get(int key) {
        if(!cache.containsKey(key)) {
            return -1;
        }
        int[] freqVal = cache.get(key);
        int freq = freqVal[0], value = freqVal[1];
        Set<Integer> keys = this.frequencies.get(freq);
        keys.remove(key);
        if(freq == this.minf && keys.isEmpty()) {
            this.minf += 1;
        }
        insert(key, freq+1, value);
        return value;
    }
    public void put(int key, int val) {
        if(this.capacity <= 0) {
            return;
        }
        int[] freqVal = this.cache.get(key);
        if(freqVal != null) {
            this.cache.put(key, new int[] {freqVal[0], val});
            this.get(key);
            return;
        } else if(this.capacity == this.cache.size()) {
            Set<Integer> minFreqKeys = this.frequencies.get(this.minf);
            int keyToDelete = minFreqKeys.iterator().next();
            this.cache.remove(keyToDelete);
            minFreqKeys.remove(keyToDelete);
        }
        this.minf = 1;
        insert(key, 1, val);
    }
}
