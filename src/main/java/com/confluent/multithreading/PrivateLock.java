package com.confluent.multithreading;

import javax.annotation.concurrent.GuardedBy;

public class PrivateLock {

    private static Object myLock = new Object();

    @GuardedBy("myLoc") private  Widget widget;

    public void someMethod() {
        synchronized (myLock) {

        }
    }




    public interface Widget {
    }
}
