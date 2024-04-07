package com.rate_limiter;

import com.rate_limiter.model.Request;

import java.util.UUID;

public class TestRateLimiter {
    public static void main(String[] args) {
        TestRateLimiter tr = new TestRateLimiter();
        tr.testRateLimiter();
    }

    private void testRateLimiter() {
        RateLimiter rateLimiter = new RateLimiterImpl(1, 3);
        String sourceIp = "127.0.0.1";
        UUID clientId = UUID.randomUUID();
        /*int requestCount = 1;
        System.out.printf("Request Count %d is allowed: %b\n", requestCount,
                rateLimiter.allow(new Request(System.currentTimeMillis(), clientId, sourceIp)));
        requestCount += 1;
        System.out.printf("Request Count %d is allowed: %b\n",requestCount,
                rateLimiter.allow(new Request(System.currentTimeMillis(), clientId, sourceIp)));
        requestCount += 1;
        System.out.printf("Request Count %d is allowed: %b\n",requestCount,
                rateLimiter.allow(new Request(System.currentTimeMillis(), clientId, sourceIp)));

        requestCount += 1;*/


        for(int i = 1; i <= 10; i++) {
            System.out.printf("Request Count %d is allowed: %b\n", i,
                    rateLimiter.allow(new Request(System.currentTimeMillis(), clientId, sourceIp)));
            if(i == 5) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
