package com.list.skiplist;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;

public class SkipList {
    SkipListNode head;
    Random rand;

    public SkipList() {
        this.head = new SkipListNode(-1);
        this.rand = new Random();
    }

    public boolean search(int target) {
        SkipListNode curr = this.head;
        while(curr != null) {
            while (curr.getNext() != null && curr.getNext().getVal() < target) {
                curr = curr.getNext();
            }
            if(curr.getNext() != null && curr.getNext().getVal() == target) {
                return true;
            }
            curr = curr.getDown();
        }
        return false;
    }
    public void insert(int num) {
        SkipListNode curr = this.head;
        Deque<SkipListNode> stack = new ArrayDeque<>();
        while(curr != null) {
            while(curr.getNext() != null && curr.getNext().getVal() < num) {
                curr = curr.getNext();
            }
            stack.push(curr);
            curr = curr.getDown();
        }
        boolean insert = true;
        SkipListNode down = null;
        while(insert && !stack.isEmpty()) {
            curr = stack.pop();
            curr.setNext(new SkipListNode(num, curr.getNext(), down));
            down = curr.getNext();
            insert = rand.nextDouble() < 0.5;
        }
        if(insert) {
            this.head = new SkipListNode(-1, null, this.head);
        }
    }
    public boolean remove(int num) {
        SkipListNode curr = this.head;
        boolean found = false;
        while(curr != null) {
            while(curr.getNext() != null && curr.getNext().getVal() < num) {
                curr = curr.getNext();
            }
            if(curr.getNext() != null && curr.getNext().getVal() == num) {
                found = true;
                curr.setNext(curr.getNext().getNext());
            }
            curr = curr.getDown();
        }
        return found;
    }
}
