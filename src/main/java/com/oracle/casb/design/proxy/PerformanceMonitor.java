package com.oracle.casb.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class PerformanceMonitor implements InvocationHandler {
    private final Object proxiedInstance;

    public PerformanceMonitor(Object proxiedInstance) {
        this.proxiedInstance = proxiedInstance;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long t0 = System.nanoTime();
        Object result = method.invoke(proxiedInstance, args);
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println("Invocation of method " + method.getName() + "() took " + millis + " ms");
        return result;
    }
}
