package com.uber;

public class Trie {
    TrieNode root;

    public Trie() {
        this.root = new TrieNode('/');
    }

    public void insert(String word) {
        TrieNode curr = this.root;
        for(char ch: word.toCharArray()) {
            if(curr.children[ch-'a'] == null) {
                curr.children[ch-'a'] = new TrieNode(ch);
            }
            curr = curr.children[ch-'a'];
            curr.counter += 1;
        }
        curr.wordCount += 1;
    }

    public int countWordsEqualTo(String word) {
        TrieNode curr = this.root;
        for(char ch: word.toCharArray()) {
            if(curr.children[ch-'a'] == null) {
                return 0;
            }
            curr = curr.children[ch-'a'];
        }
        return curr.wordCount;
    }

    public int countWordsStartingWith(String prefix) {
        TrieNode curr = this.root;
        for(char ch: prefix.toCharArray()) {
            if(curr.children[ch-'a'] == null) {
                return 0;
            }
            curr = curr.children[ch-'a'];
        }
        return curr.counter;
    }

    public void erase(String word) {
        TrieNode curr = this.root;
        TrieNode parent = null;
        for(char ch: word.toCharArray()) {
            parent = curr;
            curr = curr.children[ch-'a'];
            curr.counter -= 1;
            if(curr.counter == 0) {
                parent.children[ch-'a'] = null;
            }
        }
        curr.wordCount -= 1;
    }

    class TrieNode {
        char ch;
        TrieNode[] children;
        int counter;
        int  wordCount;
        public TrieNode(char ch) {
            this.ch = ch;
            this.children = new TrieNode[26];
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        trie.insert("apple");
        int count = trie.countWordsEqualTo("apple");
        System.out.printf("Count apple: %d\n", count);
        count = trie.countWordsStartingWith("app");
        System.out.printf("Count prefix app: %d\n", count);
        trie.erase("apple");
        count = trie.countWordsEqualTo("apple");
        System.out.printf("Count apple: %d\n", count);
        count = trie.countWordsStartingWith("app");
        System.out.printf("Count prefix app: %d\n", count);
        trie.erase("apple");
        count = trie.countWordsStartingWith("app");
        System.out.printf("Count prefix app: %d\n", count);
    }
}
