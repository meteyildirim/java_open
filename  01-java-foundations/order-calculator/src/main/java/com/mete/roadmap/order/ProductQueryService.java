package com.mete.roadmap.order;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        if (productRepository == null) {
            throw new IllegalArgumentException("Product repository cannot be null");
        }
        this.productRepository = productRepository;
    }

    public List<Product> findProductsCheaperThan(BigDecimal maximumPrice) {
        validatePrice(maximumPrice);
        return productRepository.findAll().stream()
                .filter(product -> product.getUnitPrice().compareTo(maximumPrice) < 0)
                .toList();
    }

    public List<Product> findProductsMoreExpensiveThan(BigDecimal minimumPrice) {
        validatePrice(minimumPrice);
        return productRepository.findAll().stream()
                .filter(product -> product.getUnitPrice().compareTo(minimumPrice) > 0)
                .toList();
    }

    public List<Product> searchByName(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Search text cannot be null or blank");
        }
        String lowerCaseText = text.trim().toLowerCase();
        return productRepository.findAll().stream()
                .filter(product -> product.getName().toLowerCase().contains(lowerCaseText))
                .toList();
    }

    public List<Product> getProductsSortedByPrice() {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getUnitPrice))
                .toList();
    }

    public List<Product> getProductsSortedByPriceDescending() {
        return productRepository.findAll().stream()
                .sorted(Comparator.comparing(Product::getUnitPrice).reversed())
                .toList();
    }

    public Optional<Product> findCheapestProduct() {
        return productRepository.findAll().stream()
                .min(Comparator.comparing(Product::getUnitPrice));
    }

    public Optional<Product> findMostExpensiveProduct() {
        return productRepository.findAll().stream()
                .max(Comparator.comparing(Product::getUnitPrice));
    }

    private void validatePrice(BigDecimal price) {
        if (price == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}