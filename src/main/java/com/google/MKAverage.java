package com.google;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeMap;

public class MKAverage {
    int m, k;
    Deque<Integer> queue = new ArrayDeque<>();
    SortedList left;
    SortedList mid;
    SortedList right;

    public MKAverage(int m, int k) {
        this.m = m;
        this.k = k;
        this.left = new SortedList();
        this.right = new SortedList();
        this.mid = new SortedList();
    }

    public void addElement(int num) {
        queue.addLast(num);
        // add in the proper place
        if (left.isEmpty() || num <= left.getLast()) {
            left.add(num);
        } else if (mid.isEmpty() || num <= mid.getLast()) {
            mid.add(num);
        } else {
            right.add(num);
        }
        if (queue.size() > m) {
            int removedElement = queue.removeFirst();
            // remove in the proper place
            if (left.contains(removedElement)) {
                left.remove(removedElement);
            } else if (mid.contains(removedElement)) {
                mid.remove(removedElement);
            } else {
                right.remove(removedElement);
            }
        }
        // adjust size of l1, l2, l3
        if (left.size > k) {
            mid.add(left.removeLast());
        } else if (left.size < k && !mid.isEmpty()) {
            left.add(mid.removeFirst());
        }
        if (mid.size > m -2*k) {
            right.add(mid.removeLast());
        } else if (mid.size < m -2*k && !right.isEmpty()) {
            mid.add(right.removeFirst());
        }
    }

    public int calculateMKAverage() {
        if(queue.size() < m) {
            return -1;
        }
        return (int)Math.floor((double)(mid.sum) / (mid.size));

    }
    private class SortedList {
        long sum;
        int size;
        TreeMap<Integer, Integer> tm = new TreeMap<>();
        public SortedList() {
            this.sum = 0l;
            this.size = 0;
            this.tm = new TreeMap<>();
        }
        public  int size() {
            return this.size;
        }
        public void add(int num) {
            sum += num;
            size += 1;
            tm.merge(num, 1, Integer::sum);
        }
        public void remove(int key) {
            sum -= key;
            size -= 1;
            tm.merge(key, -1, Integer::sum);
            if(tm.get(key) == 0) {
                tm.remove(key);
            }
        }
        public boolean contains(int val) {
            return tm.containsKey(val);
        }
        public boolean isEmpty() {
            return tm.isEmpty();
        }

        public int removeLast() {
            var maxEntry = tm.lastEntry();
            sum -= maxEntry.getKey();
            size -= 1;
            if(maxEntry.getValue() == 1) {
                tm.remove(maxEntry.getKey());
            } else {
                tm.merge(maxEntry.getKey(), -1, Integer::sum);
            }
            return maxEntry.getKey();
        }
        public int removeFirst() {
            var minEntry = tm.firstEntry();
            sum -= minEntry.getKey();
            size -= 1;
            if(minEntry.getValue() == 1) {
                tm.remove(minEntry.getKey());
            } else {
                tm.merge(minEntry.getKey(), -1, Integer::sum);
            }
            return minEntry.getKey();
        }
        public int getLast() {
            return tm.lastKey();
        }
        public int getFirst() {
            return tm.firstKey();
        }
    }

    public static void main(String[] args) {
        MKAverage mk = new MKAverage(6,1);
        mk.addElement(3);
        mk.addElement(1);
        mk.addElement(12);
        mk.addElement(5);
        mk.addElement(3);
        mk.addElement(4);
        int avg = mk.calculateMKAverage();
        System.out.printf("1: Avg: %d\n", avg);

        /**
         * left:    1
         * mid:     3, 5, 3, 4= 15/4 = 3
         * right:  12
         */
    }
}
