import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.Random;

public class Deadlockfree implements Runnable {
    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();
    private final Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        Thread myThread1 = new Thread(new Deadlockfree(), "thread-1");
        Thread myThread2 = new Thread(new Deadlockfree(), "thread-2");
        myThread1.start();
        myThread2.start();

        // Start monitoring thread
        new Thread(() -> {
            ThreadMXBean bean = ManagementFactory.getThreadMXBean();
            while (true) {
                long[] threadIds = bean.findDeadlockedThreads();
                if (threadIds != null) {
                    System.out.println("⚠ Deadlock detected among threads: ");
                    for (long id : threadIds) {
                        System.out.println("Thread ID: " + id);
                    }
                    System.exit(1);
                }
                try {
                    Thread.sleep(1000); // Check every 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Monitor-Thread").start();
    }

    public void run() {
        for (int i = 0; i < 10000; i++) {
            // Always lock resource1 before resource2
            System.out.println(" [ " + Thread.currentThread().getName() + " ] Trying to lock resource 1.");
            synchronized (resource1) {
                System.out.println(" [ " + Thread.currentThread().getName() + " ] Locked resource 1.");
                try {
                    Thread.sleep(10); // Simulate some work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(" [ " + Thread.currentThread().getName() + " ] Trying to lock resource 2.");
                synchronized (resource2) {
                    System.out.println(" [ " + Thread.currentThread().getName() + " ] Locked resource 2.");
                }
            }
        }
    }
}
