package week10;

public class ProducerConsumerDemo {
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
                notify(); // or use notifyAll();
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
        Thread consumerThread = new Thread(() -> shared.consumer());

        consumerThread.start(); // Start consumer first so it waits
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        producerThread.start(); // Start producer
    }
}
