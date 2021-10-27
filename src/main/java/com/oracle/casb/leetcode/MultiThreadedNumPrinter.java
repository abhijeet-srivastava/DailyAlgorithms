package com.oracle.casb.leetcode;

import java.util.Arrays;

/**
 * Created By : abhijsri
 * Date  : 2019-01-14
 **/
public class MultiThreadedNumPrinter {
    public static void main(String[] args) {
        MultiThreadedNumPrinter mt = new MultiThreadedNumPrinter();
        mt.test();
    }

    public void test() {
        int maxCount = 89;
        int numThread = 10;
        Integer[] array = { 1 };
        //Thread[] thArray = new Thread[numThread];
        for (int i = 0; i < numThread; i++) {
            Runnable runnable = new NumberPrinter(array, numThread, i , maxCount);
            Thread t = new Thread(runnable);
            t.start();
            //thArray[i] = new Thread(runnable);
            //printerThread.start();
        }
        /*for (int i = 0; i < numThread; i++) {
            thArray[i].start();
        }*/
    }

    private class NumberPrinter implements Runnable {
        private final Integer[] lock;
        private final Integer count;
        private final Integer threadNumber;
        private Integer maxCount;

        public NumberPrinter(Integer[] lock, Integer count, Integer threadNumber, Integer maxCount) {
            this.lock = lock;
            this.count = count;
            this.threadNumber = threadNumber;
            this.maxCount = maxCount;
        }

        @Override public void run() {
            while (lock[0] < maxCount) {
                try {
                    synchronized (lock) {
                        while (lock[0] % count != threadNumber) {
                            lock.wait();
                        }
                        if (lock[0] >= maxCount) {
                            lock.notifyAll();
                            break;
                        }
                        System.out.printf("%d in %s\n", lock[0], Thread.currentThread().getName());
                        lock[0] += 1;
                        lock.notifyAll();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
