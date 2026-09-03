package com.codesignal;

public interface RateLimiter {
    boolean isAllowed(String clientId);
}
