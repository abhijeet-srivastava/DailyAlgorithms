package com.leetcode.mothly.sep24;

public class MyCircularDeque {
    Node front;
    Node rear;
    int capacity;
    int size;
    public MyCircularDeque(int k) {
        this.capacity = k;
        this.size = 0;
    }

    public boolean insertFront(int value) {
        if(isFull()) {
            return false;
        }
        this.size += 1;
        Node node = new Node(value);
        if(this.front == null) {
            this.front = node;
            this.rear = node;
            this.front.prev = this.rear;
            this.rear.next = this.front;
        } else {
            this.front.next = node;
            node.prev = this.front;
            this.front = node;
            node.next = this.rear;
            this.rear.prev = node;
        }
        return true;
    }

    public boolean insertLast(int value) {
        if(isFull()) {
            return false;
        }
        this.size += 1;
        Node node = new Node(value);
        if(this.rear == null) {
            this.front = node;
            this.rear = node;
            this.front.next = this.rear;
            this.rear.prev = this.front;
        } else {
            node.next = this.rear;
            this.rear.prev = node;
            this.rear = node;
            this.front.next = node;
            node.prev = this.front;
        }
        return true;
    }

    public boolean deleteFront() {
        if(isEmpty()) {
            return false;
        }
        this.size -= 1;
        if(this.size == 0) {
            this.front = null;
            this.rear = null;
        } else {
            Node frnt = this.front;
            Node prev = frnt.prev;
            prev.next = this.rear;
            this.rear.prev = prev;
            this.front = prev;
        }
        return true;
    }

    public boolean deleteLast() {
        if(isEmpty()) {
            return false;
        }
        this.size -= 1;
        if(this.size == 0) {
            this.front = null;
            this.rear = null;
        } else {
            Node last = this.rear;
            Node next = last.next;
            next.prev = this.front;
            this.front.next = next;
            this.rear = next;
        }
        return true;
    }

    public int getFront() {
        if(isEmpty()) {
            return -1;
        }
        return this.front.value;
    }

    public int getRear() {
        if(isEmpty()) {
            return -1;
        }
        return this.rear.value;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isFull() {
        return this.size == this.capacity;
    }
    private class Node {
        int value;
        Node next;
        Node prev;
        public Node(int val) {
            this.value = val;
        }
    }

    public static void main(String[] args) {
        test1();

    }

    private static void test1() {
        MyCircularDeque mcd = new MyCircularDeque(5);
        System.out.printf("Res= %b\n", mcd.insertFront(7));
        System.out.printf("Res= %b\n", mcd.insertLast(0));
        System.out.printf("Res= %d\n", mcd.getFront());
        System.out.printf("Res= %b\n", mcd.insertLast(3));
        System.out.printf("Res= %d\n", mcd.getFront());
        System.out.printf("Res= %b\n", mcd.insertFront(9));
        System.out.printf("Res= %d\n", mcd.getRear());
        System.out.printf("Res= %d\n", mcd.getFront());
        System.out.printf("Res= %d\n", mcd.getFront());
        System.out.printf("Res= %b\n", mcd.deleteLast());
        System.out.printf("Res= %d\n", mcd.getRear());
    }
}
