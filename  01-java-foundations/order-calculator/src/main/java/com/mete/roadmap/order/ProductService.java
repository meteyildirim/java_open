package com.mete.roadmap.order;

import java.math.BigDecimal;

public final class ProductService {

    private final ProductRepository repository;

    public ProductService(
            ProductRepository repository
    ) {
        if (repository == null) {
            throw new IllegalArgumentException(
                    "Repository cannot be null"
            );
        }

        this.repository = repository;
    }

    public Product registerProduct(
            String code,
            String name,
            BigDecimal unitPrice
    ) {
        ProductCode productCode =
                new ProductCode(code);

        if (repository.existsByCode(productCode)) {
            throw new DuplicateProductException(
                    productCode
            );
        }

        Product product =
                new Product(
                        productCode,
                        name,
                        unitPrice
                );

        repository.save(product);

        return product;
    }

    public Product getProduct(String code) {
        ProductCode productCode = new ProductCode(code);

        return repository
                .findByCode(productCode)
                .orElseThrow(
                        () -> new ProductNotFoundException(productCode)
                );
    }
}