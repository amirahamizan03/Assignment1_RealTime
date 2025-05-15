package week5;

import java.util.Scanner;

class MyThread extends Thread {
    private volatile boolean running = true;

    public void run() {
        int counter = 0;
        while (running) {
            System.out.println("Thread is running - Message #" + ++counter);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread has been stopped");
    }

    public void shutdown() {
        running = false;
    }
}

     class MyVolatile {
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();

        System.out.println("Press ENTER to stop the thread...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        myThread.shutdown();
        scanner.close();
    }
}