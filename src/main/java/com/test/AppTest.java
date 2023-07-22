package com.test;


public class AppTest {
    public static void main(String[] args) {
        AppTest at = new AppTest();
        at.testString();
    }

    private void testString() {
        char[] keys = new char[26];
        keys[3] = 'a';
        System.out.printf("State: %s\n", new String(keys));
    }
}
