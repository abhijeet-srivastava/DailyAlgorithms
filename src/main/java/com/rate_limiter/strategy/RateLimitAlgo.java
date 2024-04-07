package com.rate_limiter.strategy;

import com.rate_limiter.model.Request;

public interface RateLimitAlgo {

    public boolean isRequestAllowed(Request request);
}
