package com.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AutocompleteSystem {
    TrieNode root;
    StringBuilder sb;

    public AutocompleteSystem(String[] sentences, int[] times) {
        this.sb = new StringBuilder();
        this.root = new TrieNode('/');
        for(int i = 0; i < sentences.length; i++) {
            TrieNode curr = root;
            for(char ch: sentences[i].toCharArray()) {
                int idx = ch == ' ' ? 26 : ch - 'a';
                if(curr.children[idx] == null) {
                    curr.children[idx] = new TrieNode(ch);
                }
                TrieNode child = curr.children[idx];
                child.sentenceDegree.put(sentences[i], times[i]);
                curr = child;
            }
            curr.isLeaf = true;
            curr.degree = times[i];
        }
    }

    public List<String> input(char c) {
        List<String> result = new ArrayList<>();
        if(c == '#') {
            appendToTrie(sb.toString());
            sb.setLength(0);
            return result;
        }
        this.sb.append(c);
        TrieNode curr = this.root;
        for(int i = 0; i < this.sb.length(); i++) {
            char ch = sb.charAt(i);
            int idx = ch == ' ' ? 26 : ch - 'a';
            if(curr.children[idx] == null) {
                return result;
            }
            curr = curr.children[idx];
        }
        PriorityQueue<Map.Entry<String, Integer>> pq
                = new PriorityQueue<>(
                (e1, e2) -> e1.getValue() ==  e2.getValue() ? e1.getKey().compareTo(e2.getKey()) : e1.getValue().compareTo(e2.getValue()));
        for(var entry: curr.sentenceDegree.entrySet()) {
            pq.offer(entry);
            if(pq.size() > 3) {
                pq.remove();
            }
        }
        while(!pq.isEmpty()) {
            result.add(0, pq.remove().getKey());
        }
        return result;
    }

    private void appendToTrie(String str) {
        TrieNode curr = this.root;
        for(char ch : str.toCharArray()) {
            int idx = ch == ' ' ? 26 : ch - 'a';
            if(curr.children[idx] == null) {
                curr.children[idx] = new TrieNode(ch);
            }
            TrieNode child = curr.children[idx];
            child.sentenceDegree.merge(str, 1, Integer::sum);
            curr = child;
        }
        curr.isLeaf = true;
    }
    public class TrieNode {
        char val;
        TrieNode[] children;
        boolean isLeaf;
        int degree;
        Map<String, Integer> sentenceDegree;

        public TrieNode(char ch) {
            this.val = ch;
            this.children = new TrieNode[27];
            this.isLeaf = false;
            this.sentenceDegree = new HashMap<>();
        }
    }
}
