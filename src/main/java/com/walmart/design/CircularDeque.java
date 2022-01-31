package com.walmart.design;

class CircularDeque {
    private class Node {
        Integer value;
        Node next;
        Node prev;
        public Node(Integer val) {
            this.value = val;
        }
    }
    private Node front;
    private Node rear;
    int size;
    int maxSize;

    public CircularDeque(int k) {
        this.front = new Node(null);
        this.rear = new Node(null);
        this.front.next = this.rear;
        this.front.prev = this.rear;
        this.rear.next = this.front;
        this.rear.prev = this.front;
        this.maxSize = k;
        this.size = 0;
    }

    public boolean insertFront(int value) {
        if(isFull()) {
            return false;
        }
        Node node = new Node(value);
        insertBetween(this.front, this.front.next, node);
        size += 1;
        System.out.printf("Inserted Front: %d, So size become: %d\n", value, size);
        //printList();
        return true;
    }

    public boolean insertLast(int value) {
        if(isFull()) {
            return false;
        }
        Node node = new Node(value);
        insertBetween(this.rear.prev, this.rear, node);
        size += 1;
        System.out.printf("Inserted Rear: %d, So size become: %d\n", value, size);
        //printList();
        return true;
    }

    private void insertBetween(Node precedessor, Node successor, Node node) {
        precedessor.next = node;
        node.prev = precedessor;
        node.next = successor;
        successor.prev = node;
    }

    public boolean deleteFront() {
        if(isEmpty()) {
            return false;
        }
        Node node = this.front.next;

        Node successor = node.next;
        this.front.next = successor;
        successor.prev = this.front;
        size -= 1;
        System.out.printf("Deleted Front: So size become: %d\n", size);
        //printList();
        return true;
    }

    public boolean deleteLast() {
        if(isEmpty()) {
            return false;
        }
        Node node = this.rear.prev;
        Node precedessor = node.prev;
        this.rear.prev = precedessor;
        precedessor.next = this.rear;
        size -= 1;
        System.out.printf("Deleted Last: So size become: %d\n", size);
        //printList();
        return true;
    }

    public int getFront() {
        if(isEmpty()) {
            return -1;
        }
        return this.front.next.value;
    }

    public int getRear() {
        if(isEmpty()) {
            return -1;
        }
        System.out.printf("Getting value from rear while size: %d\n", size);
        return this.rear.prev.value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size >= maxSize;
    }
    private void printList() {
        Node current = this.front.next;
        System.out.printf("Front <=>");
        while(current != this.rear) {
            System.out.printf("[%d] <=>", current.value);
            current = current.next;
        }
        System.out.printf("Rear\n");

    }
}

/**
 * Your CircularDeque object will be instantiated and called as such:
 * CircularDeque obj = new CircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */
