import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import java.math.BigDecimal;
import com.mete.roadmap.order.OrderCalculator;
import org.junit.Test;


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
}