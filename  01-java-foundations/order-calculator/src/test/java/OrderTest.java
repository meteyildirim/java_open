import com.mete.roadmap.order.Order;

import com.mete.roadmap.order.OrderItem;
import com.mete.roadmap.order.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class OrderTest {

    @Test
    public void newOrderShouldHaveZeroItems () {
        Order order = new Order();
        assertEquals(0, order.getItemCount());

    }

    @Test
    public void newOrderShouldHaveZeroSubtotal () {
        Order order = new Order();
        assertEquals(0, BigDecimal.ZERO.compareTo(order.subtotal()));


    }

    @Test
    void shouldAddItem() {
        Order order = new Order();
        Product product = new Product("Mechanical Keyboard", new BigDecimal("80.00"));
        OrderItem item = new OrderItem(product, 1);

        order.addItem(item);

        assertEquals(1, order.getItemCount());
    }

    @Test
    void shouldCountQuantitiesAcrossOrderItems() {
        Order order = new Order();

        Product keyboard =
                new Product(
                        "Keyboard",
                        new BigDecimal("50.00")
                );

        Product mouse =
                new Product(
                        "Mouse",
                        new BigDecimal("20.00")
                );

        order.addItem(new OrderItem(keyboard, 2));
        order.addItem(new OrderItem(mouse, 3));

        assertEquals(5, order.getItemCount());
    }

    @Test
    void shouldCalculateSubtotalForMultipleItems() {
        Order order = new Order();
        Product keyboard = new Product("Keyboard", new BigDecimal("50.00")); // 2 * $50 = $100
        Product mouse = new Product("Mouse", new BigDecimal("20.00"));       // 3 * $20 = $60

        order.addItem(new OrderItem(keyboard, 2));
        order.addItem(new OrderItem(mouse, 3));

        BigDecimal expectedSubtotal = new BigDecimal("160.00");
        assertEquals(0, expectedSubtotal.compareTo(order.subtotal()));
    }

    @Test
    void shouldRejectNullOrderItem() {
        Order order = new Order();

        assertThrows(
                IllegalArgumentException.class,
                () -> order.addItem(null)
        );
    }

    @Test
    void shouldCalculateTotalWithZeroDiscount() {
        Order order = new Order();
        Product product = new Product("Keyboard", new BigDecimal("100.00"));
        order.addItem(new OrderItem(product, 1));

        BigDecimal total = order.totalAfterDiscount(BigDecimal.ZERO);

        assertEquals(0, new BigDecimal("100.00").compareTo(total));
    }

    @Test
    void shouldCalculateTotalWithTenPercentDiscount() {
        Order order = new Order();
        Product product = new Product("Keyboard", new BigDecimal("100.00"));
        order.addItem(new OrderItem(product, 1));

        BigDecimal total = order.totalAfterDiscount(new BigDecimal("10"));

        assertEquals(0, new BigDecimal("90.00").compareTo(total));
    }

    @Test
    void shouldCalculateTotalWithOneHundredPercentDiscount() {
        Order order = new Order();
        Product product = new Product("Keyboard", new BigDecimal("100.00"));
        order.addItem(new OrderItem(product, 1));

        BigDecimal total = order.totalAfterDiscount(new BigDecimal("100"));

        assertEquals(0, BigDecimal.ZERO.compareTo(total));
    }

    // ==========================================
    // Discount Validation Tests
    // ==========================================

    @Test
    void shouldRejectNullDiscount() {
        Order order = new Order();

        assertThrows(
                IllegalArgumentException.class,
                () -> order.totalAfterDiscount(null)
        );
    }

    @Test
    void shouldRejectNegativeDiscount() {
        Order order = new Order();

        assertThrows(
                IllegalArgumentException.class,
                () -> order.totalAfterDiscount(new BigDecimal("-1.00"))
        );
    }

    @Test
    void shouldRejectDiscountAboveOneHundred() {
        Order order = new Order();

        assertThrows(
                IllegalArgumentException.class,
                () -> order.totalAfterDiscount(new BigDecimal("100.01"))
        );
    }

}
