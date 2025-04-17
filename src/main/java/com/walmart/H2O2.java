package com.walmart;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class H2O2 {

    private Semaphore semH;
    private Semaphore semO;

    private CyclicBarrier barrier;

    public H2O2() {
        this.semH = new Semaphore(2);
        this.semO = new Semaphore(2);
        this.barrier = new CyclicBarrier(4);
    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        this.semH.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        try {
            this.barrier.await();
        } catch (BrokenBarrierException bbex) {
            bbex.printStackTrace();
        }
        this.semH.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        this.semO.acquire();
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        try {
            this.barrier.await();
        } catch (BrokenBarrierException bbex) {
            bbex.printStackTrace();
        }
        this.semO.release();
    }
}
