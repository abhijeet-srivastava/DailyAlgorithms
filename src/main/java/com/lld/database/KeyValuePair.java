package com.lld.database;

public interface KeyValuePair {
    String get(String key);

    void put(String key, String value);

    void delete(String key);
}
