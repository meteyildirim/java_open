package com.mete.roadmap.order;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(OrderId orderId) {
    }
}
