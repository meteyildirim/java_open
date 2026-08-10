package com.mete.roadmap.order;

import java.math.BigDecimal;

public final class Product {

    private final ProductCode code;
    private final String name;
    private final BigDecimal unitPrice;

    public Product(
            ProductCode code,
            String name,
            BigDecimal unitPrice
    ) {
        if (code == null) {
            throw new IllegalArgumentException(
                    "Product code cannot be null"
            );
        }

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Product name cannot be null or blank"
            );
        }

        if (unitPrice == null
                || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                    "Unit price must be greater than zero"
            );
        }

        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public ProductCode getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
}