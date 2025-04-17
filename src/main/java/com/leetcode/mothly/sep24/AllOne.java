package com.leetcode.mothly.sep24;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class AllOne {
    Map<String, Integer> counter;
    TreeMap<Integer, Set<String>> bucket;

    public AllOne() {
        this.counter = new HashMap<>();
        this.bucket = new TreeMap<>();
    }

    public void inc(String key) {
        int prevFreq = counter.getOrDefault(key, 0);
        counter.merge(key, 1, Integer::sum);
        if(prevFreq > 0) {
            bucket.get(prevFreq).remove(key);
            if(bucket.get(prevFreq).isEmpty()) {
                bucket.remove(prevFreq);
            }
        }
        bucket.computeIfAbsent(prevFreq + 1, a -> new HashSet<>()).add(key);
        System.out.printf("Incremented: %s\n", key);
    }

    public void dec(String key) {
        int prevFreq = counter.get(key);
        bucket.get(prevFreq).remove(key);
        if(bucket.get(prevFreq).isEmpty()) {
            bucket.remove(prevFreq);
        }
        counter.put(key, prevFreq-1);
        if(prevFreq > 1) {
            bucket.computeIfAbsent(prevFreq-1, a -> new HashSet<>()).add(key);
        }
        System.out.printf("Decremented: %s\n", key);
    }

    public String getMaxKey() {
        if(this.bucket.isEmpty()) {
            return "";
        }
        String key = this.bucket.lastEntry().getValue().iterator().next();
        System.out.printf("Max Key: %s\n", key);
        return key;
    }

    public String getMinKey() {
        if(this.bucket.isEmpty()) {
            return "";
        }
        String key =  this.bucket.firstEntry().getValue().iterator().next();
        System.out.printf("MinKey: %s\n", key);
        return key;
    }

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        AllOne ao = new AllOne();
        ao.inc("a");
        ao.inc("b");
        ao.inc("b");
        ao.inc("b");
        ao.inc("b");
        ao.dec("b");
        ao.dec("b");
        System.out.printf("Max Key: %s\n", ao.getMaxKey());
        System.out.printf("Min Key: %s\n", ao.getMinKey());
    }
}
