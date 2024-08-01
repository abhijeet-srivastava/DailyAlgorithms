package com.confluent.multithreading;

import java.util.function.Consumer;

public class SynchronizedExample {

    public static void main(String[] args) {
        Consumer<String> func = (String param) -> {
            synchronized (SynchronizedExample.class){
                System.out.println(Thread.currentThread().getName() + " step1 : " + param);
                try {
                    Thread.sleep((long) Math.random() * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " step2 : " + param);
            }
        };
        Thread t1 = new Thread(() -> {
            func.accept("Param_1");
        }, "Thread_1");

        Thread t2 = new Thread(() -> {
            func.accept("Param_2");
        }, "Thread_2");
        t1.start();
        t2.start();
    }
}
