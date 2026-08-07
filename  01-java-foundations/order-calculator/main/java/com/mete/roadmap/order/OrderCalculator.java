package com.mete.roadmap.order;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class OrderCalculator {

    private static final BigDecimal ONE_HUNDRED =
            new BigDecimal("100");

    private static final BigDecimal TAX_PERCENT =
            new BigDecimal("20");

    private OrderCalculator() {
    }

    public static void main(String[] args) {
        String productName = "Mechanical Keyboard";
        BigDecimal unitPrice = new BigDecimal("79.90");
        int quantity = 2;
        BigDecimal discountPercent = new BigDecimal("10");

        validateInput(
                productName,
                unitPrice,
                quantity,
                discountPercent
        );

        BigDecimal subtotal =
                calculateSubtotal(unitPrice, quantity);

        BigDecimal discountAmount =
                calculatePercentage(subtotal, discountPercent);

        BigDecimal discountedSubtotal =
                subtotal.subtract(discountAmount);

        BigDecimal taxAmount =
                calculatePercentage(discountedSubtotal, TAX_PERCENT);

        BigDecimal total =
                discountedSubtotal.add(taxAmount);

        printResult(
                productName,
                unitPrice,
                quantity,
                subtotal,
                discountAmount,
                taxAmount,
                total
        );
    }

    static void validateInput(
            String productName,
            BigDecimal unitPrice,
            int quantity,
            BigDecimal discountPercent
    ) {
        if (productName == null || productName.isBlank()) {
            throw new IllegalArgumentException("Product name must not be null or blank.");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Unit price must not be null or negative.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        if (discountPercent == null || discountPercent.compareTo(BigDecimal.ZERO) <= 0 || discountPercent.compareTo(ONE_HUNDRED) > 0) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100.");
        }
    }

    static BigDecimal calculateSubtotal(
            BigDecimal unitPrice,
            int quantity
    ) {
        return money(unitPrice.multiply(BigDecimal.valueOf(quantity)));
    }

    static BigDecimal calculatePercentage(
            BigDecimal amount,
            BigDecimal percentage
    ) {
        BigDecimal rawPercentage = amount.multiply(percentage).divide(ONE_HUNDRED, 4, RoundingMode.HALF_UP);
        return money(rawPercentage);
    }

    static BigDecimal money(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    static void printResult(
            String productName,
            BigDecimal unitPrice,
            int quantity,
            BigDecimal subtotal,
            BigDecimal discountAmount,
            BigDecimal taxAmount,
            BigDecimal total
    ) {
        System.out.println("========================================");
        System.out.println("            ORDER SUMMARY              ");
        System.out.println("========================================");
        System.out.printf("Product Name:      %s%n", productName);
        System.out.printf("Unit Price:        $%s%n", money(unitPrice));
        System.out.printf("Quantity:          %d%n", quantity);
        System.out.println("----------------------------------------");
        System.out.printf("Subtotal:          $%s%n", money(subtotal));
        System.out.printf("Discount:         -$%s%n", money(discountAmount));
        System.out.printf("Tax (20%%):        +$%s%n", money(taxAmount));
        System.out.println("----------------------------------------");
        System.out.printf("Total Amount:      $%s%n", money(total));
        System.out.println("========================================");
    }
}