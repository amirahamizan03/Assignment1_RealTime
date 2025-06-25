package week2_3;

public class MyThreadThread extends Thread {
    public static void main(String[] args) {
        new MyThreadThread().start();
        new MyThreadThread().start();
    }

    @Override
    public void run() {
        try {
            for (int x = 0; x < 1000; x++) {
                System.out.println(Thread.currentThread().getName() + ": " + x);
                sleep(2000); // Thread.sleep(2000) also works
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
