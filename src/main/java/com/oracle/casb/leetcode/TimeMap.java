package com.oracle.casb.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeMap {

    public static void main(String[] args) {
        TimeMap tm = new TimeMap();
        tm.set("love", "high", 10);
        tm.set("love", "low", 20);
        System.out.println(tm.get("love", 5));
        System.out.println(tm.get("love", 10));
        System.out.println(tm.get("love", 15));
        System.out.println(tm.get("love", 20));
        System.out.println(tm.get("love", 25));
    }

    Map<String, TreeMap<Integer, String>> map;

    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        TreeMap<Integer, String> valueMap = map.get(key);
        if (valueMap == null) {
            valueMap = new TreeMap<>();
        }
        valueMap.put(Integer.valueOf(timestamp), value);
        map.put(key, valueMap);
    }

    public String get(String key, int timestamp) {
        TreeMap<Integer, String> valueMap = map.get(key);
        if (valueMap == null) {
            return "";
        }
        Integer floorKey = valueMap.floorKey(Integer.valueOf(timestamp));

        return (floorKey == null) ? "" : valueMap.getOrDefault(floorKey, "");

    }
}
