package com.mete.roadmap.order;

public final class ProductNotFoundException
        extends RuntimeException {

    public ProductNotFoundException(
            ProductCode code
    ) {
        super(
                "Product not found: "
                        + code.value()
        );
    }
}