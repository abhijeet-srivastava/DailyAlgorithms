package com.oracle.casb;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public interface RateLimiterTest {

    public interface RateLimiter {
        public boolean toThrottle(String serviceName);
    }

    public static void main(String[] args) {
        Map<String, Integer> services = new HashMap<>();
        services.put("api1", 5);
        services.put("api2", 2);
        RateLimiter rl = new TimeBasedRateLimiter(services);
        System.out.printf("Call to throttle: %b\n", rl.toThrottle("api1"));
        System.out.printf("Call to throttle: %b\n", rl.toThrottle("api1"));
        System.out.printf("Call to throttle: %b\n", rl.toThrottle("api1"));
        //System.out.printf("Call to throttle: %b\n", rl.toThrottle("api2"));
        //System.out.printf("Call to throttle: %b\n", rl.toThrottle("api2"));
        System.out.printf("Call to throttle: %b\n", rl.toThrottle("api1"));
        System.out.printf("Call to throttle: %b\n", rl.toThrottle("api1"));
        System.out.printf("Call to throttle: %b\n", rl.toThrottle("api1"));
        try {
            Thread.sleep(1001l);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("Call to throttle: %b\n", rl.toThrottle("api1"));
    }

    public class TimeBasedRateLimiter implements RateLimiter {

        Map<String, Integer> serviceLimits;
        Map<String, Deque<Long>> queueMap;

        //TreeMap<Integer>

        public TimeBasedRateLimiter(Map<String, Integer> services) {
            this.serviceLimits = services;
            this.queueMap = new HashMap<>();
            for (String service : services.keySet()) {
                queueMap.put(service, new ArrayDeque<Long>());
            }
        }

        public boolean toThrottle(String serviceName) {
            if(!this.serviceLimits.containsKey(serviceName)) {
                return false;
            }
            int limit = serviceLimits.get(serviceName);//10
            Deque<Long> queue = this.queueMap.get(serviceName);
            long current = System.currentTimeMillis();
            if (queue.size() < limit) {
                queue.offer(current);
                return false;
            }
            long lastSecTs = current - 1000;
            long firstCallTs = queue.getFirst();
            while (firstCallTs <= lastSecTs) {
                queue.remove(firstCallTs);
                if(!queue.isEmpty())  {
                    break;
                }

                firstCallTs = queue.getFirst();
            }
            if (queue.size() < limit) {
                queue.offer(current);
                return false;
            }
            return true;

        }
    }
}
