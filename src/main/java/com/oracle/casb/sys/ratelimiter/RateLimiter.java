package com.oracle.casb.sys.ratelimiter;

import java.util.*;
import java.util.stream.Collectors;

public class RateLimiter {
    private final Deque<Long> deque;
    private long THRESHOLD;
    //Threshold in seconds
    public RateLimiter(int thresHold) {
        this.THRESHOLD = thresHold*1000;
        this.deque = new ArrayDeque<>();
    }

    private boolean isAllowed() {
        Long currentTime = Calendar.getInstance().getTimeInMillis();
        if(deque.size() < this.THRESHOLD) {
            deque.addLast(currentTime);
            return true;
        } else if(deque.peekFirst() + this.THRESHOLD < currentTime) {
            deque.removeFirst();
            deque.addLast(currentTime);
            return true;
        }
        return false;
    }
    public String customSortString(String order, String s) {
        Map<Character, Integer> ordinal = new HashMap<>();
        for(int i = 0; i < order.length(); i++) {
            ordinal.put(order.charAt(i), i);
        }
        int len = s.length();
        return s.chars()
                .mapToObj(ch -> (char)ch)
                .sorted(Comparator.comparingInt(a -> ordinal.getOrDefault(a, len)))
                .map(ch -> Character.toString(ch))
                .collect(Collectors.joining(""));
    }
}
