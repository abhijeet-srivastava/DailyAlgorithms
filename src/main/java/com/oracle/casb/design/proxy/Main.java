package com.oracle.casb.design.proxy;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {
        Service service = new ServiceToBeMonitored();
        PerformanceMonitor handler = new PerformanceMonitor(service);
        Service proxy = (Service) Proxy
                .newProxyInstance(service.getClass().getClassLoader(), new Class[]{ Service.class }, handler);
        proxy.foobar();
    }
}
