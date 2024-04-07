package com.rate_limiter;

import com.rate_limiter.model.Request;
import com.rate_limiter.strategy.RateLimitAlgo;
import com.rate_limiter.strategy.SlidingWindowRateLimitAlgo;

public class SingletonRateLimiterImpl implements RateLimiter{

    private static SingletonRateLimiterImpl INSTANCE;
    RateLimitAlgo rateLimitAlgo;

    private SingletonRateLimiterImpl(Integer windowSize, Integer tokens) {
        this.rateLimitAlgo = new SlidingWindowRateLimitAlgo(windowSize, tokens);
    }


    public static SingletonRateLimiterImpl getInstance(Integer windowSize, Integer tokens) {
        if(INSTANCE != null) {
            return INSTANCE;
        }
        synchronized (SingletonRateLimiterImpl.class) {
            if(INSTANCE == null) {
                INSTANCE = new SingletonRateLimiterImpl(windowSize, tokens);
            }
            return INSTANCE;
        }
    }
    @Override
    public boolean allow(Request request) {
        return rateLimitAlgo.isRequestAllowed(request);
    }
}
