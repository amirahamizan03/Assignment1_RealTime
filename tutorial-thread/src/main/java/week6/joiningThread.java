package week6;

import java.util.Random;

public class joiningThread implements Runnable {

    private Random rand = new Random(System.currentTimeMillis());

    @Override
    public void run() {
        // Simulate some CPU-expensive task
        for (int i = 0; i < 100000000; i++) {
            rand.nextInt();
        }
        System.out.println("[" + Thread.currentThread().getName() + "] finished.");
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[5];

        // Start all threads
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new joiningThread(), "joinThread-" + i);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        System.out.println("[" + Thread.currentThread().getName() + "] All threads have finished.");
    }
}
