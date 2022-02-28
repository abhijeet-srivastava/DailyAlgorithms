package com.oracle.casb.leetcode;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class BoundedBlockingQueue {
    private  ReentrantLock lock;
    private Condition isFull;
    private Condition isEmpty;
    private final int[] queue;
    private int size;
    private int head;
    private int tail;

    public BoundedBlockingQueue(int capacity) {
        this.lock = new ReentrantLock();
        this.isFull = this.lock.newCondition();
        this.isEmpty = this.lock.newCondition();
        this.queue = new int[capacity];
        this.size = 0;
        this.head = 0;
        this.tail = 0;
    }

    public void enqueue(int element) throws InterruptedException {
        this.lock.lock();
        try {
            while(this.size == this.queue.length) {
                this.isFull.await();
            }
            this.queue[this.tail] = element;
            this.tail += 1;
            this.tail %= this.queue.length;
            this.size += 1;
            this.isEmpty.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public int dequeue() throws InterruptedException {
        this.lock.lock();
        try {
            while(this.size == this.queue.length) {
                this.isEmpty.await();
            }
            int res = this.queue[this.head];
            this.head += 1;
            this.head %= this.queue.length;
            this.size -= 1;
            this.isFull.signalAll();
            return res;
        }finally {
            this.lock.unlock();
        }
    }

    public int size() {
        this.lock.lock();
        try{
            return this.size;
        } finally {
            this.lock.unlock();
        }
    }
}