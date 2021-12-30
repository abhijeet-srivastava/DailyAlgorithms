package com.leetcode.mothly.dec21;

public class RangeModule {
    private class Range {
        int start;
        int end;
        Range next;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    Range head;
    public RangeModule() {
        head = new Range(0,0);
    }

    public void addRange(int left, int right) {
        if(head.next == null) {
            head.next = new Range(left, right);
        }else {
            Range prev = head;
            Range current = head.next;
        }
    }

    public boolean queryRange(int left, int right) {
        return true;
    }

    public void removeRange(int left, int right) {

    }
}
