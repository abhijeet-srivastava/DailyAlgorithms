package com.confluent.multithreading;

import java.util.concurrent.atomic.AtomicBoolean;

public class SyncronizedPrinter {
    public static void main(String[] args) {
        SyncronizedPrinter sp = new SyncronizedPrinter();
        sp.testPingPong();
    }

    private void testPingPong() {
        AtomicBoolean isPingPrinted = new  AtomicBoolean(false);
        Thread pingPrinter = new Thread(new Printer("Ping", "lock", isPingPrinted, 10));
        Thread pongPrinter = new Thread(new Printer("Pong", "lock", isPingPrinted, 10));
        pingPrinter.start();
        pongPrinter.start();

        try {
            pingPrinter.join();
            pongPrinter.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private class Printer implements Runnable{
        private String word;
        private Object lock;
        private AtomicBoolean isPingPrinted;
        private int count;

        public Printer(String word, Object lock, AtomicBoolean isPingPrinted, int maxCount) {
            this.word = word;
            this.lock = lock;
            this.isPingPrinted = isPingPrinted;
            this.count = maxCount;
        }
        @Override
        public void run() {
            while (this.count-- > 0) {
                {
                    try {
                        if (this.word.equals("Ping")) {
                            synchronized (lock){
                                while (isPingPrinted.get()) {
                                    lock.wait();
                                }
                                System.out.printf("%s", this.word);
                                isPingPrinted.compareAndExchange(false, true);
                                lock.notifyAll();
                            }
                        } else {
                            synchronized (lock){
                                while (!isPingPrinted.get()) {
                                    lock.wait();
                                }
                                System.out.printf("%s\n", this.word);
                                isPingPrinted.compareAndExchange(true, false);
                                lock.notifyAll();
                            }
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
