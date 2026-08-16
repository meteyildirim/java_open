package com.mete.roadmap.order;

// Threadsafe
public class SynchronizedCounter {

    private int value = 0;

    // Mutex lock guarantees mutual exclusion across threads
    public synchronized void increment() {
        value++;
    }

    public synchronized int getValue() {
        return value;
    }
}