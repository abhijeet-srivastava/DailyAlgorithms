package com.walmart.design;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DesignTest {
    public static void main(String[] args) {
        DesignTest dt = new DesignTest();
       //dt.testDequeue();
        dt.testCircularQueue();
    }

    private void testCircularQueue() {
        CircularQueue queue = new CircularQueue(3);
        System.out.printf("Enqueue: %d: %b\n", 1,queue.enQueue(1));
        System.out.printf("Enqueue: %d: %b\n", 2,queue.enQueue(2));
        System.out.printf("Enqueue: %d: %b\n", 3,queue.enQueue(3));
        System.out.printf("Enqueue:%d: %b\n", 4,queue.enQueue(4));
        System.out.printf("Rear: %d\n", queue.Rear());
        System.out.printf("IsFull: %b\n", queue.isFull());
        System.out.printf("Deque:  %b\n", queue.deQueue());
        System.out.printf("Enqueue: %d: %b\n", 4,queue.enQueue(4));
        System.out.printf("Rear: %d\n   ", queue.Rear());
    }

    private void testDequeue() {
        CircularDeque deque = new CircularDeque(77);
        deque.insertFront(89);
        deque.deleteLast();
        deque.insertFront(19);
        System.out.printf("Size: %d\n", deque.size);
    }
}
