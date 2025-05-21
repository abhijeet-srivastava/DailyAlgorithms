package com.leetcode.mothly.may25;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K,V> {

    private Map<K, CacheNode<K,V>> cache;

    private CacheNode<K,V> head;
    private CacheNode<K,V> tail;

    private int capacity;
    private int size;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.cache = new HashMap<>();
    }

    public V get(K key) {
        CacheNode<K, V> node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }
    public CacheNode<K,V> getNode(K key) {
        if(!this.cache.containsKey(key)) {
            return null;
        }
        CacheNode<K, V> node = this.cache.get(key);
        moveToFront(node);
        return node;
    }

    public void put(K key, V val) {
        CacheNode<K,V> node = getNode(key);
        if(node != null) {
            node.setValue(val);
            return;
        }
        node = new CacheNode<>(key, val);
        this.cache.put(key, node);
        addToFront(node);
        this.size += 1;
        if(this.size > this.capacity) {
            CacheNode<K,V> tail = popTail();
            cache.remove(tail.getKey());
            this.size -= 1;
        }
    }

    private CacheNode<K,V> popTail() {
        CacheNode<K,V> tail = this.tail;
        CacheNode<K, V> next = this.tail.next;
        this.tail = next;
        return tail;
    }

    private void moveToFront(CacheNode<K,V> node) {
        if(node == this.head) {
            return;
        }
        removeNode(node);
        addToFront(node);
    }

    private void addToFront(CacheNode<K,V> node) {
        if(this.size == 0) {
            this.head = node;
            this.tail = node;
        } else {
            node.prev = this.head;
            this.head.next = node;
            this.head = node;
        }
    }

    private void removeNode(CacheNode<K,V> node) {
        if(this.size == 0) {
            return;
        } else if(node == this.head) {
            CacheNode<K,V> prev = this.head.prev;
            prev.next = null;
            this.head = prev;
        } else if(node == this.tail) {
            CacheNode<K, V> next = this.tail.next;
            next.prev = null;
            this.tail = next;
        } else {
            CacheNode<K,V> next = node.next;
            CacheNode<K,V> prev = node.prev;
            next.prev = prev;
            prev.next = next;
        }
    }


    public class CacheNode<K,V> {
        private K key;
        private V value;

        private CacheNode<K,V> next;
        private CacheNode<K,V> prev;
        public CacheNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public CacheNode<K, V> getNext() {
            return next;
        }

        public void setNext(CacheNode<K, V> next) {
            this.next = next;
        }

        public CacheNode<K, V> getPrev() {
            return prev;
        }

        public void setPrev(CacheNode<K, V> prev) {
            this.prev = prev;
        }
    }
}
