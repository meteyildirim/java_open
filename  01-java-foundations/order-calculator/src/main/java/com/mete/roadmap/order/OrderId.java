package com.mete.roadmap.order;

import java.util.UUID;

public record OrderId(UUID value) {

    public OrderId {
        if (value == null) {
            throw new IllegalArgumentException(
                    "Order ID cannot be null"
            );
        }
    }

    public static OrderId newId() {
        return new OrderId(
                UUID.randomUUID()
        );
    }
}