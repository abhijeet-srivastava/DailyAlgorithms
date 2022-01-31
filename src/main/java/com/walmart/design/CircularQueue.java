package com.walmart.design;

public class CircularQueue {

    class Node {
        Integer value;
        Node prev;
        Node next;
        public Node(Integer value) {
            this.value = value;
        }
    }
    Node front;
    Node rear;
    int size;
    int maxSize;

    public CircularQueue(int k) {
        this.front = new Node(null);
        this.rear = new Node(null);
        this.front.next = this.rear;
        this.front.prev = this.rear;

        this.rear.next = this.front;
        this.rear.prev = this.front;
        this.size = 0;
        this.maxSize = k;
    }

    public boolean enQueue(int value) {
        if(isFull()) {
            return false;
        }
        Node node = new Node(value);
        insertBetween(this.front, this.front.next, node);
        size += 1;
        return true;
    }
    private void insertBetween(Node precedessor, Node successor, Node node) {
        precedessor.next = node;
        node.prev = precedessor;
        successor.prev = node;
        node.next = successor;
    }

    public boolean deQueue() {
        if(isEmpty()) {
            return false;
        }
        Node node = this.rear.prev;
        deleteNode(this.rear, this.rear.prev);
        size -= 1;
        return true;
    }
    private void deleteNode(Node successor, Node node) {
        Node precedessor = node.prev;
        precedessor.next = successor;
        successor.prev = precedessor;
    }

    public int Front() {
        if(isEmpty()) {
            return -1;
        }
        return this.front.next.value;
    }

    public int Rear() {
        if(isEmpty()) {
            return -1;
        }
        return this.front.next.value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size >= maxSize;
    }
}
