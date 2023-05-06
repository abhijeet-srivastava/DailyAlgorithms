package com.amazon;

public class WordDictionary {
    class TrieNode {
        char ch;
        TrieNode[] children;
        boolean isWord;
        public TrieNode(char ch) {
            this.ch = ch;
            this.children = new TrieNode[26];
        }
    }
    TrieNode root;
    public WordDictionary() {
        this.root = new TrieNode('/');
    }

    public void addWord(String word) {
        TrieNode current = root;
        for(char ch: word.toCharArray()) {
            if(current.children[ch-'a'] == null) {
                current.children[ch-'a'] = new TrieNode(ch);
                //System.out.printf("Created node: %c unedr: %c\n", current.children[ch-'a'].ch,current.ch);
            }
            current = current.children[ch-'a'];
        }
        current.isWord = true;
    }

    public boolean search(String word) {
        System.out.printf("~~~~~Searching: %s~~~~~~\n", word);
        return search(root, 0, word);
    }
    private boolean search(TrieNode curr, int index, String word) {
        System.out.printf("Curr: %c: index: %d\n", curr.ch, index);
        if(index == word.length()) {
            return curr.isWord;
        }
        char ch = word.charAt(index);
        System.out.printf("Child node is %b\n", ch != '.' &&  curr.children[ch-'a'] == null);
        if(ch == '.') {
            for(char prob = 'a'; prob <= 'z'; prob++) {
                if(curr.children[prob-'a'] != null
                        && search(curr.children[prob-'a'], index+1, word)) {
                    return true;
                }
            }
        } else if(curr.children[ch-'a'] == null) {
            return false;
        } else {
            return search(curr.children[ch-'a'], index+1, word);
        }
        return false;
    }

    public static void main(String[] args) {
        WordDictionary wd = new WordDictionary();
        wd.addWord("bad");
        wd.addWord("dad");
        wd.addWord("mad");
        String word = "bad";
        System.out.printf("Searching:[%s]: %b\n", word, wd.search(word));
        word = ".ad";
        System.out.printf("Searching:[%s]: %b\n", word, wd.search(word));
        word = "b..";
        System.out.printf("Searching:[%s]: %b\n", word, wd.search(word));
    }

}
