package week10;

public class modifyProduceConsumerDemo {
    static class SharedData {
        private boolean dataReady = false;
        private String Data;

        public synchronized void producer() {
            try {
                System.out.println("Producer: Preparing data...");
                Thread.sleep(1000);
                Data = "Hello from producer"; // Assign actual data
                dataReady = true;
                System.out.println("Producer: Data is ready");
                notifyAll(); // Changed from notify() to notifyAll()
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public synchronized void consumer() {
            try {
                while (!dataReady) {
                    System.out.println("Consumer: Waiting for data...");
                    wait();
                }
                System.out.println("Consumer: Received -> " + Data);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        SharedData shared = new SharedData();

        Thread producerThread = new Thread(() -> shared.producer());

        // Creating multiple consumer threads to demonstrate notifyAll()
        Thread consumer1 = new Thread(() -> shared.consumer());
        Thread consumer2 = new Thread(() -> shared.consumer());

        consumer1.start();
        consumer2.start();

        try {
            Thread.sleep(500); // Give consumers time to start and wait
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        producerThread.start();
    }
}
