package com.mete.roadmap.order;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    void save(Product product);

    // Optional because, the product might not exist
    Optional<Product> findByCode(ProductCode code);

    List<Product> findAll();

    boolean existsByCode(ProductCode code);
}