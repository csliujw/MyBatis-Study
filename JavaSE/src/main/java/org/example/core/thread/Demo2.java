package org.example.core.thread;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo2 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();
        // weak CAS
        int i = atomicInteger.incrementAndGet();
        //     public final void set(int newValue) {
        //        value = newValue;
        //    }
        // 非原子性
        atomicInteger.set(2);
        // CAS
        // atomicInteger.updateAndGet()
        // ThreadLocalRandom.current()
    }
}
