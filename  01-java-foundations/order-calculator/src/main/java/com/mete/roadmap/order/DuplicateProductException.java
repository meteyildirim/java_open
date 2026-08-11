package com.mete.roadmap.order;

public final class DuplicateProductException
        extends RuntimeException {

    public DuplicateProductException(
            ProductCode code
    ) {
        super(
                "Product already exists: "
                        + code.value()
        );
    }
}