package com.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class NestedIterator implements Iterator<Integer> {
    Deque<int[]> dq;

    List<List<Integer>> nestedList;

    public NestedIterator(List<List<Integer>> nestedList) {
        this.dq = new ArrayDeque<>();
        this.nestedList = nestedList;
        for(int i = 0; i < this.nestedList.size(); i++) {
            if(this.nestedList.get(i).isEmpty()) {
                continue;
            }
            dq.addFirst(new int[] {i, 0});
        }
    }

    @Override
    public Integer next() {
        if(dq.isEmpty()) {
            throw new RuntimeException("Empty iterator");
        }
        int[] pair = dq.removeLast();// i ,j
        int ret = this.nestedList.get(pair[0]).get(pair[1]);
        pair[1] += 1;
        if(pair[1] < this.nestedList.get(pair[0]).size()) {
            this.dq.addFirst(pair);
        }
        return ret;
    }

    @Override
    public boolean hasNext() {
        return !dq.isEmpty();
    }
}
