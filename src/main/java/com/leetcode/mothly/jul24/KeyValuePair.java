package com.leetcode.mothly.jul24;

public interface KeyValuePair {

    String get(String key) throws KeyNotFoundException;
    void put(String key, String value);

    void delete(String key) throws KeyNotFoundException;
}
