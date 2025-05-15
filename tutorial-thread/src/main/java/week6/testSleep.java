package week6;

public class testSleep {
    public static void main(String[] args) {
        for (int i = 1; i <= 20; i++) {
            int threadNum = i;
            Thread t = new Thread(() -> printPattern(threadNum));
            t.start();
            try {
                t.join(); // optional if you want sequential output
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printPattern(int threadNum) {
        System.out.println("Thread " + threadNum + ":");
        System.out.println("ONE");
        sleep();
        System.out.println("TWO");
        sleep();
        System.out.println("THREE");
        sleep();
        System.out.println("xxxxxxxxxx\n");
    }

    private static void sleep() {
        try {
            Thread.sleep(200); // sleep for 200 milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
