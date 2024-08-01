package com.list;

public class MyLinkedList {

    Node head;
    int size;

    public MyLinkedList() {
        this.head = null;
        this.size = 0;
    }
    //6->3->1->7-> 4
    public int get(int index) {
        if(index >= size) {
            return -1;
        }
        Node curr = this.head;
        while(curr != null && index > 0) {
            curr = curr.next;
            index -= 1;
        }
        //printList("get: "+ index);
        return curr.val;
    }

    public void addAtHead(int val) {
        Node node = new Node(val);
        if(this.head == null) {
            this.head = node;
            size += 1;
            return;
        }
        node.next = this.head;
        this.head = node;
        size += 1;
    }

    public void addAtTail(int val) {
        Node node = new Node(val);
        if(this.head == null) {
            this.head = node;
            size += 1;
            return;
        }
        Node prev = null;
        Node curr = this.head;
        while(curr != null) {
            prev = curr;
            curr = curr.next;
        }
        prev.next = node;
        size += 1;
    }
    //1 -> 3 (size = 2)(1, 2)

    public void addAtIndex(int index, int val) {
        if(index > size) {
            return;
        } else if(index == size) {
            addAtTail(val);
            return;
        }else if(index == 0) {
            addAtHead(val);
            return;
        }
        Node prev = null;
        Node curr = head;
        while(curr != null && index > 0) {
            prev = curr;
            curr = curr.next;
            index -= 1;
        }
        Node node = new Node(val);
        prev.next = node;
        //System.out.printf("Adding %d after %d\n", val, prev.val);
        node.next = curr;
        size += 1;
        //printList("addAtIndex-" + index);
    }
    //1 -> 2 -> 3 (size = 3)
    public void deleteAtIndex(int index) {
        if(index >= size) {
            return;
        } else if(index == 0) {
            this.head = this.head.next;
            size -= 1;
            return;
        }
        Node prev = null;
        Node curr = head;
        while(curr != null && index > 0) {
            prev = curr;
            curr = curr.next;
            index -= 1;
        }
        if(curr != null)
            prev.next = curr.next;
        else {
            prev.next = null;
        }
        size -= 1;
        //printList("deleteAtIndex-" + index);
    }
    private void printList(String method) {
        int idx = 0;
        Node curr = this.head;
        System.out.printf("Method: %s\n", method);
        System.out.printf("Size: %d\n", size);
        while(curr != null) {
            System.out.printf("curr[%d] %d\n", idx, curr.val);
            curr = curr.next;
            idx += 1;
        }
    }
    public class Node {
        int val;
        Node next;
        public Node(int val) {
            this.val = val;
        }
    }

    public static void main(String[] args) {
        MyLinkedList ml = new MyLinkedList();
        ml.addAtHead(7);
        ml.addAtHead(2);
        ml.addAtHead(1);
        ml.addAtIndex(3,0);
        ml.deleteAtIndex(2);
        ml.addAtHead(6);
        ml.addAtTail(4);
        int val = ml.get(4);
        System.out.printf("val: %d\n", val);
        ml.addAtHead(4);
        ml.addAtIndex(5,0);
        ml.addAtHead(6);

    }
}
