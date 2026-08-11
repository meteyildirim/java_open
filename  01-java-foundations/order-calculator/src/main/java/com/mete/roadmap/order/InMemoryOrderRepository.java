package com.mete.roadmap.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class InMemoryOrderRepository
        implements OrderRepository {

    private final Map<OrderId, Order> orders =
            new HashMap<>();

    @Override
    public void save(Order order) {
        orders.put(
                order.getId(),
                order
        );
    }

    @Override
    public Optional<Order> findById(
            OrderId id
    ) {
        return Optional.ofNullable(
                orders.get(id)
        );
    }

    @Override
    public List<Order> findAll() {
        return List.copyOf(
                orders.values()
        );
    }
}