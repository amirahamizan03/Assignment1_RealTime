package week9;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BankAccountWithLock {

    private double balance;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    public BankAccountWithLock(double initialBalance) {
        this.balance = initialBalance;
    }

    //Read balance (shared lock)
    public double getBalance(){
        readLock.lock();

        try{
            System.out.println(Thread.currentThread().getName() + "read balance: " + balance);
            return balance;
        }finally {
            readLock.unlock();
        }
    }

    //deposit money (exclusive lock)
    public void deposit(double amount) {
        writeLock.lock();

        try{
            System.out.println(Thread.currentThread().getName() + "deposit amount: " + amount);
            balance += amount;
        }finally {
            writeLock.unlock();
        }
    }
    //withdraw money (Exclusive lock)
    public void withdraw(double amount) {
        writeLock.lock();

        try{
            if (balance >= amount) {
                System.out.println(Thread.currentThread().getName() + "withdraws: " + amount);
                balance -= amount;
            }else{
                System.out.println(Thread.currentThread().getName() + "insufficient funds for : " + amount);
            }

        } finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        BankAccountWithLock account = new BankAccountWithLock(10);

        // Thread to read balance
        Runnable reader = () -> {
            for (int i = 0; i < 3; i++) {
                account.getBalance();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Thread to deposit money
        Runnable depositor = () -> {
            for (int i = 0; i < 3; i++) {
                account.deposit(50);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Thread to withdraw money
        Runnable withdrawer = () -> {
            for (int i = 0; i < 3; i++) {
                account.withdraw(30);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Create and start threads
        Thread t1 = new Thread(reader, "ReaderThread-1 ");
        Thread t2 = new Thread(reader, "ReaderThread-2 ");
        Thread t3 = new Thread(depositor, "DepositorThread ");
        Thread t4 = new Thread(withdrawer, "WithdrawerThread ");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final balance: " + account.getBalance());
    }
}
