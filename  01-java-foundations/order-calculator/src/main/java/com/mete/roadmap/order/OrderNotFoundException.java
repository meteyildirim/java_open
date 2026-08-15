package com.mete.roadmap.order;

public class OrderNotFoundException extends OrderException {
    public OrderNotFoundException(OrderId orderId) {
        super("Order not found:" +orderId.value());
    }
}
