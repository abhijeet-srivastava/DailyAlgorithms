package com.meta;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    private class Node{
        int key;
        int value;
        Node next;
        Node prev;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
    private Map<Integer, Node> cache;
    private int size;
    private int capacity;
    private Node leastRecentlyUsed;
    private Node mostRecentlyUsed;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.cache = new HashMap<>(this.capacity>>1);
    }

    public int get(int key) {
        Node node = this.cache.get(key);
        if(node == null) {
            return -1;
        } else {
            moveToHead(node);
            return node.value;
        }
    }

    public void put(int key, int value) {
        Node node = this.cache.get(key);
        if(node == null) {
            node = new Node(key, value);
            this.cache.put(key, node);
            addNode(node);
            this.size += 1;
            if(this.size > this.capacity) {
                Node tail = popTail();
                this.cache.remove(tail.key);
                this.size-=1;
            }
        } else {
            node.value = value;
            this.cache.put(key, node);
            moveToHead(node);
        }
    }

    private Node popTail() {
        Node tail = this.leastRecentlyUsed;
        removeNode(tail);
        return tail;
    }

    private void moveToHead(Node node) {
        if (node == this.mostRecentlyUsed) {
            return;
        }
        removeNode(node);
        addNode(node);
    }

    private void addNode(Node node) {
        if(this.size == 0) {
            this.leastRecentlyUsed = node;
            this.mostRecentlyUsed = node;
        } else {
            this.mostRecentlyUsed.next = node;
            node.prev = this.mostRecentlyUsed;
            this.mostRecentlyUsed = node;
        }
    }

    private void removeNode(Node node) {
        if(this.size == 0) {
            return;
        } else if(node == this.leastRecentlyUsed) {
            Node next = this.leastRecentlyUsed.next;
            this.leastRecentlyUsed = next;
        } else {
            Node next = node.next;
            Node prev = node.prev;
            prev.next = next;
            next.prev = prev;
        }
        node.next = null;
        node.prev = null;
    }

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(10);
        cache.put(10,13);
        cache.put(3,17);
        cache.put(6,11);
        cache.put(10,5);
        cache.put(9,10);
        cache.get(13);
        cache.put(2,19);
        cache.get(2);
        cache.get(3);
        cache.put(5,25);
        cache.get(8);
        cache.put(9,22);
        cache.put(5,5);
        cache.put(1,30);
        cache.get(11);
        cache.put(9,12);
        cache.get(7);
        cache.get(5);
        cache.get(8);
        cache.get(9);
        cache.put(4,30);
        cache.put(9,3);
        cache.get(9);
        cache.get(10);
        cache.get(10);
        cache.put(6,14);
        cache.put(3,1);
        cache.get(3);
        cache.put(10,11);
        cache.get(8);
        cache.put(2,14);
        cache.get(1);
        cache.get(5);
        cache.get(4);
        cache.put(11,4);
        cache.put(12,24);
        cache.put(5,18);
        cache.get(13);
        cache.put(7,23);
        cache.get(8);
        cache.get(12);
        cache.put(3,27);
        cache.put(2,12);
        cache.get(5);
        cache.put(2,9);
        cache.put(13,4);
        cache.put(8,18);
        cache.put(1,7);
        cache.get(6);
        cache.put(9,29);
        cache.put(8,21);
        cache.get(5);
        cache.put(6,30);
        cache.put(1,12);
        cache.get(10);
        cache.put(4,15);
        cache.put(7,22);
        cache.put(11,26);
        cache.put(8,17);
        cache.put(9,29);
        cache.get(5);
        cache.put(3,4);
        cache.put(11,30);
        cache.get(12);
        cache.put(4,29);
        cache.get(3);
        cache.get(9);
        cache.get(6);
        cache.put(3,4);
        cache.get(1);
        cache.get(10);
        cache.put(3,29);
        cache.put(10,28);
        cache.put(1,20);
        cache.put(11,13);
        cache.get(3);
        cache.put(3,12);
        cache.put(3,8);
        cache.put(10,9);
        cache.put(3,26);
        cache.get(8);
        cache.get(7);
        cache.get(5);
        cache.put(13,17);
        cache.put(2,27);
        cache.put(11,15);
        cache.get(12);
        cache.put(9,19);
        cache.put(2,15);
        cache.put(3,16);
        cache.get(1);
        cache.put(12,17);
        cache.put(9,1);
        cache.put(6,19);
        cache.get(4);
        cache.get(5);
        cache.get(5);
        cache.put(8,1);
        cache.put(11,7);
        cache.put(5,2);
        cache.put(9,28);
        cache.get(1);
        cache.put(2,2);
        cache.put(7,4);
        cache.put(4,22);
        cache.put(7,24);
        cache.put(9,26);
        cache.put(13,28);
        cache.put(11,26);
    }
}
