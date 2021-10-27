package main.java.com.oracle.casb.expedia.threadpool;

public class Task implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()
                    +" is executing task.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
