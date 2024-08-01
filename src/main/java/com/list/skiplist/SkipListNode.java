package com.list.skiplist;

public class SkipListNode {
    private int val;
    private SkipListNode next;
    private SkipListNode down;

    public SkipListNode(int val) {
        this.val = val;
    }

    public SkipListNode(int val, SkipListNode next, SkipListNode down) {
        this.val = val;
        this.next = next;
        this.down = down;
    }

    public int getVal() {
        return val;
    }

    public SkipListNode getNext() {
        return next;
    }

    public void setNext(SkipListNode next) {
        this.next = next;
    }

    public void setDown(SkipListNode down) {
        this.down = down;
    }

    public SkipListNode getDown() {
        return down;
    }
}
