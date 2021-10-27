package com.oracle.casb.expedia;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleThreadpool {

    private AtomicInteger poolCount = new AtomicInteger(0);

    // Queue of runnables
    private ConcurrentLinkedQueue<Runnable> queue;
    // Flag to control the SimpleThreadpoolThread objects
    private AtomicBoolean execute;

    private List<SimpleThreadpoolThread> threadPool;

    private SimpleThreadpool(int threadCount) {
        // Increment pool count
        poolCount.incrementAndGet();
        this.queue = new ConcurrentLinkedQueue<>();
        this.execute = new AtomicBoolean(Boolean.TRUE);
        this.threadPool = new ArrayList<>();
        for (int threadIndex = 0; threadIndex < threadCount; threadCount++) {
            String threadName = String.format("SimpleThreadpool-%s-Thread-%s", poolCount.get(), threadIndex);
            SimpleThreadpoolThread thread = new SimpleThreadpoolThread(threadName, execute, queue);
            thread.start();
            this.threadPool.add(thread);
        }
    }

    public static SimpleThreadpool getInstance(int count) {
        return new SimpleThreadpool(count);
    }
    public static SimpleThreadpool getInstance() {
        return new SimpleThreadpool(Runtime.getRuntime().availableProcessors());
    }

    public void execute(Runnable runnable) {
        if(this.execute.get()) {
            this.queue.add(runnable);
        } else {
            throw new IllegalStateException("Threadpool terminating, unable to execute runnable");
        }
    }

    /**
     * Awaits up to <b>timeout</b> ms the termination of the threads in the threadpool
     *
     * @param timeout Timeout in milliseconds
     * @throws TimeoutException      Thrown if the termination takes longer than the timeout
     * @throws IllegalStateException Thrown if the stop() or terminate() methods haven't been called before awaiting
     */
    public void awaitTermination(long timeout) throws TimeoutException{
        if (this.execute.get()) {
            throw new IllegalStateException("Threadpool not terminated before awaiting termination");
        }
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime <= timeout) {
            boolean isAnyAlive = false;
            for (Thread thread : threadPool) {
                if(thread.isAlive()) {
                    isAnyAlive = true;
                    break;
                }
            }
            if (!isAnyAlive){
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new ThreadpoolException(e);
            }
        }
        throw new TimeoutException("Unable to terminate threadpool within the specified timeout (" + timeout + "ms)");
    }

    /**
     * Awaits the termination of the threads in the threadpool indefinitely
     *
     * @throws IllegalStateException Thrown if the stop() or terminate() methods haven't been called before awaiting
     */
    public void awaitTermination() throws TimeoutException{
        if(this.execute.get()) {
            throw new IllegalStateException("Threadpool not terminated before awaiting termination");
        }
        while (true) {
            boolean isAnyAlive = false;
            for (Thread thread : threadPool) {
                if (thread.isAlive()) {
                    isAnyAlive = true;
                    break;
                }
            }
            if (!isAnyAlive) {
                break;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new ThreadpoolException(e);
            }
        }
    }

    /**
     * Clears the queue of runnables and stops the threadpool. Any runnables currently executing will continue to execute.
     */
    public void terminate() {
        queue.clear();
        stop();
    }
    /**
     * Stops addition of new runnables to the threadpool and terminates the pool once all runnables in the queue are executed.
     */
    public void stop() {
        execute.set(Boolean.FALSE);
    }

    private class SimpleThreadpoolThread extends Thread {
        private AtomicBoolean execute;
        private ConcurrentLinkedQueue<Runnable> queue;

        public SimpleThreadpoolThread (String name, AtomicBoolean execute, ConcurrentLinkedQueue<Runnable> queue) {
            super(name);
            this.execute = execute;
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                // Continue to execute when the execute flag is true, or when there are runnables in the queue
                while (execute.get() || !queue.isEmpty()) {
                    Runnable runnable;
                    while ((runnable = queue.poll()) != null) {
                        runnable.run();
                    }

                    Thread.sleep(1);
                }
            } catch (InterruptedException  | RuntimeException e) {
                throw new ThreadpoolException(e);
            }
        }
    }

    private class ThreadpoolException extends RuntimeException {
        public ThreadpoolException(Exception cause) {
            super(cause);
        }
    }
}
