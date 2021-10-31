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
    int capacity;
    int currentCapacity;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.currentCapacity = 0;
        this.cache = new HashMap();
        //this.leastRecentlyUsed = this.mostRecentlyUsed = new Node();
    }


    private void addNode(Node node) {
        if(currentCapacity == 0) {
            this.leastRecentlyUsed = this.mostRecentlyUsed = node;
            return;
        }
        this.mostRecentlyUsed.next = node;
        node.previous = this.mostRecentlyUsed;
        this.mostRecentlyUsed = node;
    }
    private void removeNode(Node node) {
        Node nxt = node.next;
        Node previous = node.previous;

        if(node.key == leastRecentlyUsed.key) {
            //Left most node
            nxt.previous = null;
            leastRecentlyUsed = nxt;
        } else {
            nxt.previous = previous;
            previous.next = nxt;
        }
        node.next = null;
        node.previous = null;
    }
    private void moveToHead(Node node) {
        if(node.key == mostRecentlyUsed.key) {
            return;
        }
        removeNode(node);
        addNode(node);
    }

    private Node popTail(){
        Node node = leastRecentlyUsed;
        Node nxt = node.next;
        removeNode(node);
        leastRecentlyUsed = nxt;
        return node;
    }
    public int get(int key) {
        Node node = this.cache.get(key);
        if(node == null) {
            return -1;
        }
        this.moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if(node == null) {
            node = new Node(key, value);

            this.cache.put(key, node);
            addNode(node);
            this.currentCapacity += 1;
            if(this.currentCapacity > this.capacity){
                Node tail = popTail();
                this.cache.remove(tail.key);
                this.currentCapacity -= 1;
            }
        }else {
            node.value = value;
            moveToHead(node);
        }
    }
    /**
     * Addd a node at front of queue
     * @param node
     */

    private void addNode1(Node node) {
        if (currentCapacity == 0) {
            this.leastRecentlyUsed = this.mostRecentlyUsed = node;
            return;
        }
        node.next = mostRecentlyUsed;
        mostRecentlyUsed.previous = node;
        mostRecentlyUsed = node;
    }

    private void removeNode1(Node node) {
        Node previous = node.previous;
        Node next = node.next;
        if (node.key == leastRecentlyUsed.key && previous != null) {
            previous.next = null;
            leastRecentlyUsed = previous;
        } else {
            previous.next = next;
            next.previous = previous;
        }
        node.next = null;
        node.previous = null;
    }

    private void moveToHead1(Node node) {
        if (node.key == mostRecentlyUsed.key) {
            return;//Already at head
        }
        this.removeNode1(node);
        this.addNode1(node);
    }

    private Node popTail1() {
        Node node = leastRecentlyUsed;
        Node previous = node.previous;
        removeNode1(node);
        previous.next = null;
        leastRecentlyUsed = previous;
        return node;
    }



    public int get1(int key) {
        Node node = cache.get(key);
        if(node == null){
            return -1;
        }

        // move the accessed node to the head;
        this.moveToHead1(node);
        return node.value;
    }

    public void put1(int key, int value) {
        Node node = cache.get(key);
        if (node == null) {
            node =  new Node(key, value);
            this.cache.put(key, node);
            this.addNode1(node);

            currentCapacity += 1;

            if(currentCapacity > capacity) {
                Node tail = this.popTail1();
                this.cache.remove(tail.key);
                currentCapacity -= 1;
            }
        } else {
            node.value = value;
            this.moveToHead1(node);
        }
    }
}
