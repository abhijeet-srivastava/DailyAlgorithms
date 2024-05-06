package com.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class AllOne {
    public Map<String, Integer> keysToFreq;
    public Map<Integer, Node> freqNodes;
    Node head;
    Node tail;

    public AllOne() {
        this.keysToFreq = new HashMap<>();
        this.freqNodes = new HashMap<>();
        this.head = new Node(0);
        this.tail = new Node(Integer.MAX_VALUE);
        this.head.prev = this.tail;
        this.tail.next = this.head;
    }

    public void inc(String key) {
        if(keysToFreq.containsKey(key)) {
            int currFreq = keysToFreq.get(key);
            Node currFreqNode = freqNodes.get(currFreq);
            int nextFreq = currFreq+1;
            if(freqNodes.containsKey(nextFreq)) {
                Node nextFreqNode = freqNodes.get(nextFreq);
                nextFreqNode.addKey(key);
            } else {
                Node nextFreqNode = new Node(nextFreq);
                nextFreqNode.addKey(key);
                addBeforeNode(currFreqNode, nextFreqNode);
                freqNodes.put(nextFreq, nextFreqNode);
            }

            currFreqNode.removeKey(key);
            if(currFreqNode.isEmpty()) {
                removeFromQueue(currFreqNode);
                freqNodes.remove(currFreq);
            }

            this.keysToFreq.put(key, nextFreq);

        }  else {
            if(freqNodes.containsKey(1)) {
                Node node = freqNodes.get(1);
                node.addKey(key);
            } else {
                Node node = new Node(1);
                freqNodes.put(1, node);
                appendToHead(node);
                node.addKey(key);
            }
            keysToFreq.put(key, 1);
        }
    }

    public void dec(String key) {
        int currFreq = keysToFreq.get(key);
        Node currFreqNode = freqNodes.get(currFreq);
        keysToFreq.remove(key);
        if(currFreq > 1) {
            int prevFreq = currFreq-1;
            if(freqNodes.containsKey(prevFreq)) {
                freqNodes.get(prevFreq).addKey(key);
            } else {
                Node prevFreqNode = new Node(prevFreq);
                prevFreqNode.addKey(key);
                freqNodes.put(prevFreq, prevFreqNode);
                addAfterNode(currFreqNode, prevFreqNode);
            }
            keysToFreq.put(key, prevFreq);
        }
        currFreqNode.removeKey(key);
        if(currFreqNode.isEmpty()) {
            removeFromQueue(currFreqNode);
            freqNodes.remove(currFreq);
        }
    }

    public String getMaxKey() {
        if(this.tail.next == this.head) {
            return "";
        }
        return this.tail.next.getAnyKey();
    }

    public String getMinKey() {
        if(this.head.prev == this.tail) {
            return "";
        }
        return this.head.prev.getAnyKey();
    }
    private void removeFromQueue(Node node) {
        Node next = node.next;
        Node prev = node.prev;
        next.prev = prev;
        prev.next = next;
    }
    private void addAfterNode(Node curr, Node next) {
        Node currNext = curr.next;
        next.next = currNext;
        currNext.prev = next;
        curr.next = next;
        next.prev = curr;

    }
    private void addBeforeNode(Node curr, Node prev) {
        Node currPrev = curr.prev;
        prev.prev = currPrev;
        currPrev.next = prev;
        curr.prev = prev;
        prev.next = curr;

    }
    private void appendToHead(Node node) {
        Node prev = this.head.prev;
        prev.next = node;
        node.prev = prev;
        node.next = this.head;
        this.head.prev = node;
    }

    public class Node {
        int freq;
        Set<String> keys;
        Node next;
        Node prev;
        public Node(int freq) {
            this.freq = freq;
            this.keys = new HashSet<>();
        }

        public void removeKey(String key) {
            this.keys.remove(key);
        }
        public void addKey(String key) {
            this.keys.add(key);
        }
        public boolean isEmpty() {
            return this.keys.isEmpty();
        }
        public String getAnyKey() {
            return this.isEmpty() ? "" : this.keys.iterator().next();
        }
    }
}
