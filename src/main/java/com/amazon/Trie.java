package com.amazon;

public class Trie {
    private class Node {
        char ch;
        boolean isLeaf;
        Node[] children;
        public Node(char ch){
            this.ch = ch;
            this.isLeaf = false;
            this.children = new Node[26];
        }
    }
    private Node root;
    public Trie() {
        root = new Node('/');
    }

    public void insert(String word) {
        Node current = root;
        for(char ch: word.toCharArray()) {
            if(current.children[ch-'a'] == null) {
                current.children[ch-'a'] = new Node(ch);
            }
            current = current.children[ch-'a'];
        }
        current.isLeaf = true;
    }

    public boolean search(String word) {
        Node current = root;
        for(char ch: word.toCharArray()) {
            if(current.children[ch-'a'] == null) {
                return false;
            }
            current = current.children[ch-'a'];
        }
        return current.isLeaf;
    }

    public boolean startsWith(String prefix) {
        Node current = root;
        for(char ch: prefix.toCharArray()) {
            if(current.children[ch-'a'] == null) {
                return false;
            }
            current = current.children[ch-'a'];
        }
        return true;
    }
}
