package week5;

public class MultiplicationTableThreads {
    public static void main(String[] args) {
        // Create and start three threads
        new Thread(new MultiplicationTask(0)).start();
        new Thread(new MultiplicationTask(1)).start();
        new Thread(new MultiplicationTask(2)).start();
    }
}

class MultiplicationTask implements Runnable {
    private final int threadNumber;

    public MultiplicationTask(int threadNumber) {
        this.threadNumber = threadNumber;
    }

    @Override
    public void run() {
        int number = threadNumber + 1; // Calculate the actual number (1-3)
        for (int j = 1; j <= 3; j++) {
            System.out.printf("Thread-%d: %d * %d = %d%n",
                    threadNumber, number, j, number * j);

            try {
                Thread.sleep(10); // Small delay for interleaving
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}