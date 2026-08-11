package com.mete.roadmap.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class InMemoryProductRepository
        implements ProductRepository {

    private final Map<ProductCode, Product> products =
            new HashMap<>();

    @Override
    public void save(Product product) {
        products.put(
                product.getCode(),
                product
        );
    }

    @Override
    public Optional<Product> findByCode(
            ProductCode code
    ) {
        return Optional.ofNullable(
                products.get(code)
        );
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(
                products.values()
        );
    }

    @Override
    public boolean existsByCode(
            ProductCode code
    ) {
        return products.containsKey(code);
    }
}