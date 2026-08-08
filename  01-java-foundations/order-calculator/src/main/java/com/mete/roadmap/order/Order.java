package com.mete.roadmap.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public final class Order {

    private final List<OrderItem> items =
            new ArrayList<>();

    public void addItem(OrderItem item) {
        if (item==null){
            throw new IllegalArgumentException("Item must not be null");
        }
        this.items.add(item);

    }

    public int getItemCount() {
        return this.items.size();
    }
    public BigDecimal subtotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItem item : this.items) {
            BigDecimal itemTotal = item.getProduct().getUnitPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }

        return total;
    }
    public BigDecimal totalAfterDiscount(BigDecimal discountPercent) {
        if (discountPercent == null
                || discountPercent.compareTo(BigDecimal.ZERO) < 0
                || discountPercent.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100.");
        }

        BigDecimal currentSubtotal = subtotal();
        BigDecimal discountAmount = currentSubtotal
                .multiply(discountPercent)
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);

        return currentSubtotal.subtract(discountAmount);
    }
}