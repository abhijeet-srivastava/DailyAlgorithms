package com.leetcode.contest392;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    int capacity;
    int size;
    Map<Integer, Node> cache;
    Node head;
    Node tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = size;
        this.cache = new HashMap<>();
    }

    public int get(int key) {
        if(!this.cache.containsKey(key)) {
            return -1;
        }
        Node node = this.cache.get(key);
        moveToHead(node);
        return node.val;
    }

    public void put(int key, int value) {
        Node node = this.cache.get(key);
        if(node != null) {
            node.val = value;
            this.cache.put(key, node);
            this.get(key);
        } else {
            node = new Node(key, value);
            this.cache.put(key, node);
            appendToQueue(node);
            this.size += 1;
            if(this.size > this.capacity) {
                Node tail = popTail();
                this.cache.remove(tail.key);
                this.size -= 1;
            }
        }
    }
    private Node popTail() {
        Node tail = this.tail;
        Node next = tail.next;
        next.prev = null;
        tail.next = null;
        this.tail = next;
        return tail;
    }

    private void moveToHead(Node node) {
        if(node == this.head) {
            return;
        }
        removeNode(node);
        appendToQueue(node);
    }
    private void appendToQueue(Node node) {
        if(this.head == null) {
            this.head = node;
            this.tail = node;
        } else {
            this.head.next = node;
            node.prev = this.head;
            this.head = node;
        }
    }
    private void removeNode(Node node) {
        if(node == this.tail) {
            Node next = this.tail.next;
            this.tail = next;
        }  else {
            Node next = node.next;
            Node prev = node.prev;
            next.prev = prev;
            prev.next = next;
        }
    }

    private class Node {
        int key;
        int val;
        Node next;
        Node prev;
        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}
