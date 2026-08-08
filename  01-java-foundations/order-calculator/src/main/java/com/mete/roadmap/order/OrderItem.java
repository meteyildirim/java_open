package com.mete.roadmap.order;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class OrderItem {

    private final Product product;
    private final int quantity;

    public OrderItem(
            Product product,
            int quantity
    ) {
        if (product == null) {
            throw new IllegalArgumentException(
                    "Product cannot be null"
            );
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero"
            );
        }

        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal subtotal() {
        return product.getUnitPrice()
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }
}