package com.confluent.multithreading;

public class SynchronizedPingPong {
    public static void main(String[] args) {
        SynchronizedPingPong spp = new SynchronizedPingPong();
        spp.testPingPong();
    }

    private void testPingPong()  {
        Ping ping = new Ping("Ping", 100);
        Pong pong = new Pong("Pong", 400);
        ping.setPong(pong);
        pong.setPing(ping);
        Thread t1 = new Thread(ping);
        Thread t2 = new Thread(pong);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private class Ping implements Runnable {
        private String word;
        private int delay;
        private Pong pong;

        public Ping(String word, int delay) {
            this.word = word;
            this.delay = delay;
        }

        public void setPong(Pong pong) {
            this.pong = pong;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(word + " ");
                    Thread.sleep(delay);
                    synchronized (pong) {
                        pong.notifyAll();
                    }
                    synchronized (this) {
                        this.wait();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class Pong implements Runnable {
        private String word;
        private int delay;
        private Ping ping;

        public Pong(String word, int delay) {
            this.word = word;
            this.delay = delay;
        }

        public void setPing(Ping ping) {
            this.ping = ping;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(word + " ");
                    Thread.sleep(delay);
                    synchronized (ping) {
                        ping.notifyAll();
                    }
                    synchronized (this) {
                        this.wait();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
