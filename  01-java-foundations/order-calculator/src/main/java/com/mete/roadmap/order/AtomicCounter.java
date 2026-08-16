package com.mete.roadmap.order;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {

    // Uses hardware-level CAS (Compare-And-Swap) instructions
    private final AtomicInteger value = new AtomicInteger(0);

    public void increment() {
        value.getAndIncrement();
    }

    public int getValue() {
        return value.get();
    }
}