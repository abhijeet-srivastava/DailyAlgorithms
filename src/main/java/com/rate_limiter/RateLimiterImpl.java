package com.rate_limiter;

import com.rate_limiter.model.Request;
import com.rate_limiter.strategy.RateLimitAlgo;
import com.rate_limiter.strategy.SlidingWindowRateLimitAlgo;

public class RateLimiterImpl implements RateLimiter{
    RateLimitAlgo rateLimitAlgo;

    public RateLimiterImpl(Integer windowSize, Integer tokens) {
        this.rateLimitAlgo = new SlidingWindowRateLimitAlgo(windowSize, tokens);
    }

    @Override
    public boolean allow(Request request) {
        return rateLimitAlgo.isRequestAllowed(request);
    }
}
