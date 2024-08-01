package com.leetcode.mothly.jul24;

public class KeyNotFoundException extends Exception {

    String message;

    public KeyNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
