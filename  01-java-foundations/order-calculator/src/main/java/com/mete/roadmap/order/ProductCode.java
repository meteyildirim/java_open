package com.mete.roadmap.order;

public record ProductCode(String value) {

    public ProductCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Product code cannot be null or blank"
            );
        }

        value = value.trim().toUpperCase();
    }
}