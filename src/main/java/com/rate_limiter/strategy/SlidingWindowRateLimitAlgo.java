package com.rate_limiter.strategy;

import com.rate_limiter.model.Request;

import java.util.ArrayDeque;
import java.util.Deque;

public class SlidingWindowRateLimitAlgo implements  RateLimitAlgo {

    /**
     * Window Size in Seconds
     */
    private Integer windowSize;

    /**
     * Number of tokens allowed in current window
     */
    private Integer tokens;

    private Deque<Long> window;


    public SlidingWindowRateLimitAlgo(Integer windowSize, Integer tokens) {
        this.windowSize = windowSize;
        this.tokens = tokens;
        this.window = new ArrayDeque<>();
    }

    @Override
    public boolean isRequestAllowed(Request request) {
        long leftBoundry = System.currentTimeMillis() - windowSize*1000;
        while (!this.window.isEmpty() && this.window.peekLast() < leftBoundry) {
            this.window.removeLast();
        }
        if(this.window.size() >= tokens) {
            return false;
        }
        this.window.offerFirst(request.getTimestamp());
        return true;
    }
}
