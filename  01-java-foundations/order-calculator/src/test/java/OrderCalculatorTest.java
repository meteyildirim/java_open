
import java.math.BigDecimal;

import com.mete.roadmap.order.OrderCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderCalculatorTest {

    @Test
    void shouldCalculateSubtotal() {
        BigDecimal result = OrderCalculator.calculateSubtotal(
                new BigDecimal("79.90"),
                2
        );

        assertEquals(new BigDecimal("159.80"), result);
    }

    @Test
    void shouldCalculateTenPercent() {
        BigDecimal result = OrderCalculator.calculatePercentage(
                new BigDecimal("159.80"),
                new BigDecimal("10")
        );

        assertEquals(new BigDecimal("15.98"), result);
    }

    @Test
    void shouldRejectZeroQuantity() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OrderCalculator.validateInput(
                        "Keyboard",
                        new BigDecimal("79.90"),
                        0,
                        new BigDecimal("10")
                )
        );
    }

    @Test
    void shouldRejectZeroUnitPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OrderCalculator.validateInput(
                        "Keyboard",
                        BigDecimal.ZERO,
                        1,
                        new BigDecimal("10")
                )
        );
    }

    @Test
    void shouldRejectDiscountAboveOneHundred() {
        assertThrows(
                IllegalArgumentException.class,
                () -> OrderCalculator.validateInput(
                        "Keyboard",
                        new BigDecimal("79.90"),
                        1,
                        new BigDecimal("101")
                )
        );
    }

    @Test
    void shouldRejectNullProductName() {
        String productName =null;
        BigDecimal unitPrice = new BigDecimal("1000");
        int quantity =3;
        BigDecimal discountPercent = new BigDecimal("10");
        assertThrows(IllegalArgumentException.class,
                () -> OrderCalculator.validateInput(productName,unitPrice,quantity,discountPercent)
        );

    }
    @Test
    void shouldRejectBlankProductName() {
        String productName =" ";
        BigDecimal unitPrice = new BigDecimal("1000");
        int quantity =3;
        BigDecimal discountPercent = new BigDecimal("10");
        assertThrows(IllegalArgumentException.class,
                () -> OrderCalculator.validateInput(productName,unitPrice,quantity,discountPercent)
        );

    }

    @Test
    void shouldRejectNullPrice() {
        String productName ="Keyboard";
        BigDecimal unitPrice = null;
        int quantity =3;
        BigDecimal discountPercent = new BigDecimal("10");
        assertThrows(IllegalArgumentException.class,
                () -> OrderCalculator.validateInput(productName,unitPrice,quantity,discountPercent)
        );

    }

    @Test
    void shouldRejectNegativePrice() {
        String productName ="Keyboard";
        BigDecimal unitPrice = new BigDecimal("-10");
        int quantity =3;
        BigDecimal discountPercent = new BigDecimal("10");
        assertThrows(IllegalArgumentException.class,
                () -> OrderCalculator.validateInput(productName,unitPrice,quantity,discountPercent)
        );

    }

    @Test
    void shouldRejectNegativeQuantity() {
        String productName ="Keyboard";
        BigDecimal unitPrice = new BigDecimal("10");
        int quantity =-1;
        BigDecimal discountPercent = new BigDecimal("10");
        assertThrows(IllegalArgumentException.class,
                () -> OrderCalculator.validateInput(productName,unitPrice,quantity,discountPercent)
        );

    }

    @Test
    void shouldRejectNullDiscount() {
        String productName ="Keyboard";
        BigDecimal unitPrice = new BigDecimal("19");
        int quantity =3;
        BigDecimal discountPercent = null;
        assertThrows(IllegalArgumentException.class,
                () -> OrderCalculator.validateInput(productName,unitPrice,quantity,discountPercent)
        );

    }

    @Test
    void shouldRejectNegativeDiscount() {
        String productName ="Keyboard";
        BigDecimal unitPrice = new BigDecimal("20");
        int quantity =3;
        BigDecimal discountPercent = new BigDecimal("-10");
        assertThrows(IllegalArgumentException.class,
                () -> OrderCalculator.validateInput(productName,unitPrice,quantity,discountPercent)
        );

    }

    @Test
    void shouldAcceptZeroPercentDiscount() {
        String productName = "Keyboard";
        BigDecimal unitPrice = new BigDecimal("20.00");
        int quantity = 3;
        BigDecimal discountPercent = BigDecimal.ZERO;

        assertDoesNotThrow(
                () -> OrderCalculator.validateInput(productName, unitPrice, quantity, discountPercent)
        );
    }

    @Test
    void shouldAcceptOneHundredPercentDiscount() {
        String productName = "Keyboard";
        BigDecimal unitPrice = new BigDecimal("20.00");
        int quantity = 3;
        BigDecimal discountPercent = new BigDecimal("100");

        assertDoesNotThrow(
                () -> OrderCalculator.validateInput(productName, unitPrice, quantity, discountPercent)
        );
    }

    @Test
    void shouldRoundPercentageCorrectly() {

        BigDecimal amount = new BigDecimal("12.55");
        BigDecimal percentage = new BigDecimal("10");

        BigDecimal result = OrderCalculator.calculatePercentage(amount, percentage);

        assertEquals(new BigDecimal("1.26"), result);
    }


}