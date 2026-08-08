package com.mete.roadmap.order;

import java.math.BigDecimal;

public final class Product {

    private final String name;
    private final BigDecimal unitPrice;

    public Product(
            String name,
            BigDecimal unitPrice
    ) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name can not be null or blank");
        }

        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }

        this.name = name;
        this.unitPrice = unitPrice;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}