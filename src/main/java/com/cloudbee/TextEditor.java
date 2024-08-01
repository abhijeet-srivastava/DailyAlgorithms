package com.cloudbee;

import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditor {
    Deque<Character> left;
    Deque<Character> right;
    public TextEditor() {
        this.left = new ArrayDeque<>();
        this.right = new ArrayDeque<>();
    }

    public void addText(String text) {
        for(char ch: text.toCharArray()) {
            this.left.offerFirst(ch);
        }
    }

    public int deleteText(int k) {
        int count = 0;
        while(!this.left.isEmpty() && count < k) {
            char popped = this.left.removeFirst();
            count += 1;
        }
        return count;
    }

    public String cursorLeft(int k) {
        int count = 0;
        while(!this.left.isEmpty() && count < k) {
            char ch = this.left.removeFirst();
            this.right.offerLast(ch);
            count += 1;
        }
        if(this.left.isEmpty()) {
            return "";
        }

        return getLastKCharacters(10);
    }

    public String cursorRight(int k) {
        int count = 0;
        while(!this.right.isEmpty() && count < k) {
            char ch = this.right.removeLast();
            this.left.offerFirst(ch);
            count += 1;
        }
        return getLastKCharacters(10);
    }
    private String getLastKCharacters(int k) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        while(!left.isEmpty() && count < k) {
            char ch = this.left.removeFirst();
            this.right.offerLast(ch);
            count += 1;
        }
        while(!this.right.isEmpty() && count > 0) {
            char ch = this.right.removeLast();
            sb.append(ch);
            this.left.offerFirst(ch);
            count -= 1;
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        TextEditor te = new TextEditor();
        te.addText("leetcode");
        int del = te.deleteText(4);
        System.out.printf("1. del: %d\n", del);
        te.addText("practice");
        String val = te.cursorRight(3);
        System.out.printf("2. val: %s\n", val);
        val = te.cursorLeft(8);
        System.out.printf("3. val: %s\n", val);
        del = te.deleteText(10);
        System.out.printf("4. del: %d\n", del);
        val = te.cursorLeft(2);
        System.out.printf("5. val: %s\n", val);
        val = te.cursorRight(6);
        System.out.printf("6. val: %s\n", val);
    }
}
