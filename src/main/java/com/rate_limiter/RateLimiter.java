package com.rate_limiter;

import com.rate_limiter.model.Request;

public interface RateLimiter {
    public boolean allow(Request request);
}
