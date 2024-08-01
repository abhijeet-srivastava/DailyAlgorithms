package com.confluent.multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadedCounter {

    public static void main(String[] args) {
        ThreadedCounter tc = new ThreadedCounter();
        tc.testThreadedPrint();

    }

    private void testThreadedPrint() {
        int numOfThreads = 5;
        AtomicInteger sharedCounter = new AtomicInteger(1);
        for(int idx  = 1; idx <= numOfThreads; idx++) {
            Thread t1 = new Thread(new NumberGenerator(numOfThreads, "lock", idx%numOfThreads, sharedCounter, 1000), "Thread_" + idx);
            t1.start();
        }
    }

    private class NumberGenerator implements Runnable {
        private final int numThread;
        private final Object lock;

        private int threadId;
        private  AtomicInteger counter;
        private int maxValue;

        public NumberGenerator(int numThread, Object lock, int threadId, AtomicInteger counter, int maxValue) {
            this.numThread = numThread;
            this.lock = lock;
            this.threadId = threadId;
            this.counter = counter;
            this.maxValue = maxValue;
        }


        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            while (counter.get() <= maxValue-numThread + 1) {
                try {
                    synchronized (lock) {
                        while (counter.get() % numThread != threadId) {
                            lock.wait();
                        }
                        System.out.printf("%s - [%d]\n", threadName, counter.getAndIncrement());
                        lock.notifyAll();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
