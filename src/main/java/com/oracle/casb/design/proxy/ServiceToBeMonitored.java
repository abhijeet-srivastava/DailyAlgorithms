package com.oracle.casb.design.proxy;

import java.util.concurrent.TimeUnit;

public class ServiceToBeMonitored implements Service {
    @Override
    public void foobar() {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
