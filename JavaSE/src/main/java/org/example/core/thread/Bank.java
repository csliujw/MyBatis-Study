package org.example.core.thread;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * core java ReentrantLock
 */
public class Bank {
    // 高精度计算
    private final BigDecimal[] accounts;
    private Lock bankLock;
    private Condition sufficientFunds;
    private Condition other;

    public Bank(int n, BigDecimal initialBalance) {
        accounts = new BigDecimal[n];
        // 这个方法  我还没怎么用过呢
        Arrays.fill(accounts, initialBalance);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
        other = bankLock.newCondition();
    }

    public void transfer(int from, int to, BigDecimal amount) throws InterruptedException {
        bankLock.lock();
        try {
            // 钱不够就转
            while (accounts[from].compareTo(amount) == -1) {
                sufficientFunds.await();
            }
            System.out.println(Thread.currentThread());
            accounts[from] = accounts[from].subtract(amount);
            accounts[to] = accounts[to].add(amount);
            System.out.println("from value = " + accounts[from].doubleValue());
            // 完成操作，唤醒该条件中等待的xxx
            sufficientFunds.signalAll();
        } finally {
//            bankLock.unlock();
        }
    }

    public void testOtherCondition() {
        bankLock.lock();
        try {
            other.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("testOtherCondition is ok");
        bankLock.unlock();
    }

    public void testSameCondition() {
        bankLock.lock();
        try {
            sufficientFunds.await();
            System.out.println("testSameCondition is ok");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bankLock.unlock();
        }
    }

    public void testLock() {
        bankLock.lock();
        System.out.println("testLock is ok");
        bankLock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        Bank bank = new Bank(10, new BigDecimal("100.223"));
        Thread t1 = new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                    bank.transfer(1, 2, new BigDecimal("50.2"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            while (true) {
                System.out.println("will run test lock");
                bank.testLock();
            }
        });

        Thread t3 = new Thread(() -> {
            while (true) {
                System.out.println("will run same condition");
                bank.testSameCondition();
            }
        });

        Thread t4 = new Thread(() -> {
            while (true) {
                System.out.println("will run other condition");
                bank.testOtherCondition();
            }
        });
        t4.start();
        TimeUnit.SECONDS.sleep(2);
        t1.start();

//        t3.start();
//        t4.start();
    }
}
