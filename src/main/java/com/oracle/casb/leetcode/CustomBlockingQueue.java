package com.oracle.casb.leetcode;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CustomBlockingQueue<T> {

    private T[] queue;
    private ReentrantLock lock;
    private int capacity;

    private int size = 0;

    private int putIndex = 0;
    private int takeIndex = 0;


    private Condition consumeCond;
    private Condition produceCond;


    public CustomBlockingQueue(int capacity) {
        this.capacity = capacity;
        queue = (T[]) new Object[capacity];
        lock = new ReentrantLock();
        consumeCond = lock.newCondition();
        produceCond = lock.newCondition();
    }


    public T dequeue() {
        lock.lock();
        try {
            while (size == 0) {
                try {
                    consumeCond.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T item = queue[takeIndex];
            queue[takeIndex] = null;
            size--;
            takeIndex++;
            if (takeIndex == capacity) {
                takeIndex = 0;
            }
            produceCond.signalAll();
            return item;
        } finally {
            lock.unlock();
        }
    }

    public void enqueue(T item) {
        lock.lock();
        try {
            while (size == capacity) {
                try {
                    produceCond.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue[putIndex] = item;
            size++;
            putIndex++;
            if(putIndex == capacity) {
                putIndex = 0;
            }
            consumeCond.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
