

import com.mete.roadmap.order.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {

    private static final Currency USD = Currency.getInstance("USD");
    private static final Currency EUR = Currency.getInstance("EUR");

    @Test
    void shouldCreateMoney() {
        Money money = new Money(new BigDecimal("100.00"), USD);

        assertEquals(new BigDecimal("100.00"), money.amount());
        assertEquals(USD, money.currency());
    }

    @Test
    void shouldRoundAmount() {
        // Test rounding up (10.555 -> 10.56) and scale normalization (10.5 -> 10.50)
        Money money1 = new Money(new BigDecimal("10.555"), USD);
        Money money2 = new Money(new BigDecimal("10.5"), USD);

        assertEquals(new BigDecimal("10.56"), money1.amount());
        assertEquals(new BigDecimal("10.50"), money2.amount());
    }

    @Test
    void shouldRejectNullAmount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Money(null, USD)
        );
    }

    @Test
    void shouldRejectNullCurrency() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Money(new BigDecimal("10.00"), (Currency) null)
        );
    }

    @Test
    void shouldAddMoney() {
        Money m1 = new Money(new BigDecimal("10.50"), USD);
        Money m2 = new Money(new BigDecimal("20.25"), USD);

        Money result = m1.add(m2);

        assertEquals(new Money(new BigDecimal("30.75"), USD), result);
    }

    @Test
    void shouldSubtractMoney() {
        Money m1 = new Money(new BigDecimal("50.00"), USD);
        Money m2 = new Money(new BigDecimal("15.50"), USD);

        Money result = m1.subtract(m2);

        assertEquals(new Money(new BigDecimal("34.50"), USD), result);
    }

    @Test
    void shouldMultiplyMoney() {
        Money money = new Money(new BigDecimal("12.50"), USD);

        Money result = money.multiply(3);

        assertEquals(new Money(new BigDecimal("37.50"), USD), result);
    }

    @Test
    void shouldRejectAddingDifferentCurrencies() {
        Money usdMoney = new Money(new BigDecimal("10.00"), USD);
        Money eurMoney = new Money(new BigDecimal("10.00"), EUR);

        assertThrows(
                IllegalArgumentException.class,
                () -> usdMoney.add(eurMoney)
        );
    }

    @Test
    void shouldRejectSubtractingDifferentCurrencies() {
        Money usdMoney = new Money(new BigDecimal("50.00"), USD);
        Money eurMoney = new Money(new BigDecimal("20.00"), EUR);

        assertThrows(
                IllegalArgumentException.class,
                () -> usdMoney.subtract(eurMoney)
        );
    }

    @Test
    void shouldRejectNegativeQuantity() {
        Money money = new Money(new BigDecimal("10.00"), USD);

        assertThrows(
                IllegalArgumentException.class,
                () -> money.multiply(-1)
        );
    }
}