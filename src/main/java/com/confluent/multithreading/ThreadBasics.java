package com.confluent.multithreading;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;

public class ThreadBasics {

    @NotThreadSafe
    class NonThreadSafeCounter {
        private int counter;
        public NonThreadSafeCounter() {
            this.counter = 0;
        }
        public int incrAndGet() {
            this.counter += 1;
            return this.counter;
        }
    }

    @ThreadSafe
    class ThreadSafeCounter {
        @GuardedBy("this") private int counter;

        public int incrAndGet() {
            synchronized (this){
                this.counter += 1;
            }
            return this.counter;
        }
    }
}
