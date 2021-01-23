package org.example.lux.question;

import java.util.concurrent.locks.ReentrantLock;

public class VolatileDemo {
    public static volatile int race = 0;

    private static ReentrantLock lock = new ReentrantLock();

    public static void increase() {
//        lock.lock();
        race++;
//        lock.unlock();
    }

    private static final int THREAD_COUNT = 20;

    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    increase();
                }
            });
            threads[i].start();
        }
        // IDEA的缘故，会比eclipse格外多出一个线程进行执行，所以是activateCount>2
        while (Thread.activeCount() > 2)
            Thread.yield();
        System.out.println(race);
    }

}
