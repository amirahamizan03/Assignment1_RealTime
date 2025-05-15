package week2_3;

public class MultithreadingThing extends Thread {

    private int number;

    // Constructor (no return type)
    public MultithreadingThing(int threadNumber) {
        this.number = threadNumber;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Thread " + number + ": " + number + " * " + i + " = " + (number * i));

            try {
                Thread.sleep(1000); // 1-second delay between prints
            } catch (InterruptedException e) {
                System.out.println("Thread " + number + " was interrupted.");
            }
        }
    }
}