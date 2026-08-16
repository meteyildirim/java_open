package com.mete.roadmap.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public record Money(BigDecimal amount, Currency currency) {

    // Compact constructor for validation and normalization
    public Money {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }

        // Normalize amount to scale 2 using HALF_UP rounding
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }

    // Convenience constructor for String currency codes (e.g. "USD", "EUR")
    public Money(BigDecimal amount, String currencyCode) {
        this(amount, currencyCode == null ? null : Currency.getInstance(currencyCode));
    }

    public Money add(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Money to add cannot be null");
        }
        verifySameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Money to subtract cannot be null");
        }
        verifySameCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    public Money multiply(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative: " + quantity);
        }
        return new Money(this.amount.multiply(BigDecimal.valueOf(quantity)), this.currency);
    }

    private void verifySameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                    "Cannot perform operation on different currencies: " + this.currency + " and " + other.currency
            );
        }
    }

    // JavaBeans compatibility aliases
    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}