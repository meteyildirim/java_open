package com.mete.roadmap.order;

public abstract class OrderException extends RuntimeException {

    protected OrderException(String message){
        super(message);
    }
}
