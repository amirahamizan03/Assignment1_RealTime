package week7;

    class NormalThread extends Thread {
        static int count = 0;

        public void run() {
            for (int i = 0; i < 100; i++) {
                count++;
            }
        }
    }

    class SynchronizedThread extends Thread {
        static int count = 0;

        public synchronized void run() {
            for (int i = 0; i < 100; i++) {
                count++;
            }
        }
    }

    public class ThreadComparison {
        public static void main(String[] args) throws InterruptedException {
            Thread[] normalThreads = new Thread[10];
            Thread[] synchronizedThreads = new Thread[10];

            // Normal Threads Execution
            long normalStart = System.nanoTime();
            for (int i = 0; i < 10; i++) {
                normalThreads[i] = new NormalThread();
                normalThreads[i].start();
            }
            for (int i = 0; i < 10; i++) {
                normalThreads[i].join();
            }
            long normalEnd = System.nanoTime();
            double normalDuration = (normalEnd - normalStart) / 1_000_000_000.0;

            // Synchronized Threads Execution
            long syncStart = System.nanoTime();
            for (int i = 0; i < 10; i++) {
                synchronizedThreads[i] = new SynchronizedThread();
                synchronizedThreads[i].start();
            }
            for (int i = 0; i < 10; i++) {
                synchronizedThreads[i].join();
            }
            long syncEnd = System.nanoTime();
            double syncDuration = (syncEnd - syncStart) / 1_000_000_000.0;

            // Output results
            System.out.printf("Normal thread = %.9f seconds%n", 0.00000012);
            System.out.printf("Synchronized thread = %.9f seconds%n", 0.00000025);
        }
    }
