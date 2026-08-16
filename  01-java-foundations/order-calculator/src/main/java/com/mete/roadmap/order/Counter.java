package com.mete.roadmap.order;



public class Counter {

    private int value = 0;

    public void increment() {
        // NOT atomic: 3-step operation (Read value, Add 1, Write value)
        value++;
    }

    public int getValue() {
        return value;
    }
}
