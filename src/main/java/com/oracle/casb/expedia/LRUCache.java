package com.oracle.casb.expedia;

import java.util.HashMap;
import java.util.Map;

class LRUCache {
    public static void main(String[] args) {
        LRUCache lCache = new LRUCache(2);
        lCache.put(2, 1);
        lCache.put(1, 1);
        //System.out.printf("Get[%d] = %d\n", 1, lCache.get(1));
        lCache.put(2, 3);
        //System.out.printf("Get[%d] = %d\n", 2, lCache.get(2));
        lCache.put(4, 1);
        System.out.printf("Get[%d] = %d\n", 1, lCache.get(1));
        System.out.printf("Get[%d] = %d\n", 2, lCache.get(2));
        //System.out.printf("Get[%d] = %d\n", 4, lCache.get(4));
    }

    private class Node{
        int key;
        int value;
        Node next;
        Node previous;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public Node() {
        }
    }
    Map<Integer, Node> cache;
    Node leastRecentlyUsed;
    Node mostRecentlyUsed;
    int maxCapacity;
    int currentCapacity;
    public LRUCache(int capacity) {
        this.maxCapacity = capacity;
        this.currentCapacity = 0;
        this.cache = new HashMap();
        //this.leastRecentlyUsed = this.mostRecentlyUsed = new Node();
    }


    /**
     * Addd a node at front of queue
     * @param node
     */

    private void addNode(Node node) {
        if (currentCapacity == 0) {
            this.leastRecentlyUsed = this.mostRecentlyUsed = node;
            return;
        }
        node.next = mostRecentlyUsed;
        mostRecentlyUsed.previous = node;
        mostRecentlyUsed = node;
    }

    private void removeNode(Node node) {
        Node prev = node.previous;
        Node next = node.next;
        if (node.key == leastRecentlyUsed.key && prev != null) {
            prev.next = null;
            leastRecentlyUsed = prev;
        } else {
            prev.next = next;
            next.previous = prev;
        }
        node.next = null;
        node.previous = null;
    }

    private void moveToHead(Node node) {
        if (node.key == mostRecentlyUsed.key) {
            return;//Already at head
        }
        this.removeNode(node);
        this.addNode(node);
    }

    private Node popTail() {
        Node node = leastRecentlyUsed;
        Node prev = node.previous;
        removeNode(node);
        prev.next = null;
        leastRecentlyUsed = prev;
        return node;
    }



    public int get(int key) {
        Node node = cache.get(key);
        if(node == null){
            return -1;
        }

        // move the accessed node to the head;
        this.moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node == null) {
            node =  new Node(key, value);
            this.cache.put(key, node);
            this.addNode(node);

            currentCapacity += 1;

            if(currentCapacity > maxCapacity) {
                Node tail = this.popTail();
                this.cache.remove(tail.key);
                currentCapacity -= 1;
            }
        } else {
            node.value = value;
            this.moveToHead(node);
        }
    }
}
