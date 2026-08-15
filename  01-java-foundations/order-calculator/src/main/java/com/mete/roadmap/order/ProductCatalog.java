
package com.mete.roadmap.order;

import java.util.*;

public final class ProductCatalog {

    private final Map<ProductCode, Product> products = new HashMap<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (products.containsKey(product.getCode())) {
            throw new IllegalArgumentException("Product code already exists");
        }

        products.put(product.getCode(), product);
    }

    public Optional<Product> findByCode(ProductCode code) {
        return Optional.ofNullable(products.get(code));
    }

    public int size() {
        return products.size();
    }

    public Set<ProductCode> getProductCodes() {
        return Set.copyOf(products.keySet());
    }

    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    public List<Product> searchByName(String searchText) {
        if (searchText == null || searchText.isBlank()) {
            throw new IllegalArgumentException("Search text cannot be null or blank");
        }

        return products.values()
                .stream()
                .filter(product -> product.getName()
                        .toLowerCase()
                        .contains(searchText.trim().toLowerCase()))
                .toList();
    }

    public List<Product> getProductsSortedByName() {
        return products.values()
                .stream()
                .sorted(Comparator.comparing(Product::getName))
                .toList();
    }

    public List<Product> getProductsSortedByPrice() {
        return products.values()
                .stream()
                .sorted(Comparator.comparing(Product::getUnitPrice))
                .toList();
    }

    public List<Product> getProductsSortedByPriceDescending() {
        return products.values()
                .stream()
                .sorted(Comparator.comparing(Product::getUnitPrice).reversed())
                .toList();
    }
}