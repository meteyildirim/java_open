package com.mete.roadmap.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class Order {

    private final List<OrderItem> items = new ArrayList<>();
    private final OrderId id;
    private OrderStatus status = OrderStatus.DRAFT;

    public Order(OrderId id) {
        this.id = id;
    }

    public OrderId getId() {
        return id;
    }

    public void addItem(OrderItem item) {
        if (status != OrderStatus.DRAFT) {
            throw new IllegalStateException(
                    "Items can only be added to draft orders"
            );
        }

        if (item == null) {
            throw new IllegalArgumentException(
                    "Item must not be null"
            );
        }

        items.add(item);
    }

    public int getItemCount() {
        return items.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
    }

    public BigDecimal subtotal() {
        return items.stream()
                .map(item -> item.getProduct().getUnitPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public List<OrderItem> getItems() {
        return List.copyOf(items);
    }

    public BigDecimal totalAfterDiscount(BigDecimal discountPercent) {
        if (discountPercent == null
                || discountPercent.compareTo(BigDecimal.ZERO) < 0
                || discountPercent.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100.");
        }

        BigDecimal currentSubtotal = subtotal();
        BigDecimal discountAmount = currentSubtotal
                .multiply(discountPercent)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        return currentSubtotal.subtract(discountAmount);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void confirm() {
        if (items.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot confirm an empty order"
            );
        }

        if (status != OrderStatus.DRAFT) {
            throw new IllegalStateException(
                    "Only draft orders can be confirmed"
            );
        }

        status = OrderStatus.CONFIRMED;
    }

    public void pay() {
        if (status != OrderStatus.CONFIRMED) {
            throw new IllegalStateException(
                    "Only confirmed orders can be paid"
            );
        }

        status = OrderStatus.PAID;
    }

    public void ship() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException(
                    "Only paid orders can be shipped"
            );
        }

        status = OrderStatus.SHIPPED;
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED) {
            throw new IllegalStateException(
                    "Shipped orders cannot be cancelled"
            );
        }

        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Order is already cancelled"
            );
        }

        status = OrderStatus.CANCELLED;
    }

    public int getLineCount() {
        return items.size();
    }
}