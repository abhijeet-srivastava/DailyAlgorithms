package com.walmart;

import java.util.HashMap;
import java.util.Map;

class LRUCache {
    public static void main(String[] args) {
        LRUCache lc = new LRUCache(3);
        //["LRUCache","put","put","put","put","get","get","get","get","put","get","get","get","get","get"]
        //[[3],[1,1],[2,2],[3,3],[4,4],[4],[3],[2],[1],[5,5],[1],[2],[3],[4],[5]]
        lc.put(1,1);
        lc.put(2,2);
        lc.put(3,3);
        lc.put(4,4);
        int res = lc.get(4);
        res = lc.get(3);
        res = lc.get(2);
        res = lc.get(1);
        lc.put(5,5);
        res = lc.get(1);
        res = lc.get(2);
        res = lc.get(3);
        res = lc.get(4);
        res = lc.get(5);
    }

    class Node {
        int key;
        int val;
        Node next;
        Node prev;
        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
    Map<Integer, Node> cache;
    int capacity;
    Node leastRecentlyUsed;
    Node mostRecentlyUsed;
    int currCapacity;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.currCapacity = 0;
        this.cache = new HashMap<>();
        Node node = new Node(-1, -1);
        this.leastRecentlyUsed = this.mostRecentlyUsed = node;
    }
    private void addNode(Node node) {
        if(this.currCapacity == 0) {
            this.leastRecentlyUsed = this.mostRecentlyUsed = node;
            return;
        }
        this.mostRecentlyUsed.next = node;
        node.prev = this.mostRecentlyUsed;
        this.mostRecentlyUsed = node;
    }
    private void removeNode(Node node) {
        if(this.currCapacity == 0) {
            return;
        }
        if(node.key == this.leastRecentlyUsed.key) {
            Node next = this.leastRecentlyUsed.next;
            next.prev = null;
            this.leastRecentlyUsed = next;
        } else {
            Node next = node.next;
            Node prev = node.prev;
            next.prev = prev;
            prev.next = next;
        }
        node.next = null;
        node.prev = null;
    }
    private void moveToHead(Node node) {
        if(node.key == this.mostRecentlyUsed.key) {
            return;
        }
        removeNode(node);
        addNode(node);
    }

    private Node popFromTail() {
        Node node = this.leastRecentlyUsed;
        //Node next = node.next;
        removeNode(node);
        //next.prev = null;
        return node;
    }
    public int get(int key) {
        Node node = this.cache.get(key);
        if(node == null) {
            return -1;
        }
        //removeNode(node);
        moveToHead(node);
        return node.val;
    }

    public void put(int key, int value) {
        System.out.printf("Put key[%d] = %d\n", key, value);
        Node node = this.cache.get(key);
        if(node == null) {
            node = new Node(key, value);
            addNode(node);
            this.cache.put(key, node);
            this.currCapacity += 1;
            if(this.currCapacity > this.capacity) {
                Node tail = popFromTail();
                this.cache.remove(tail.key);
                this.currCapacity -= 1;
            }
        } else {
            node.val = value;
            moveToHead(node);
            this.cache.put(key, node);
        }
    }
}
