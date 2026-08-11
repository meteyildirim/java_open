import com.mete.roadmap.order.*;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class OrderTest {

    @Test
    public void newOrderShouldHaveZeroItems () {
        Order order = new Order(OrderId.newId());
        assertEquals(0, order.getItemCount());

    }

    @Test
    public void newOrderShouldHaveZeroSubtotal () {
        Order order = new Order(OrderId.newId());
        assertEquals(0, BigDecimal.ZERO.compareTo(order.subtotal()));


    }

    @Test
    void shouldAddItem() {
        Order order = new Order(OrderId.newId());
        ProductCode code = new ProductCode("  KB-001  ");
        Product product = new Product(code,"Mechanical Keyboard", new BigDecimal("80.00"));
        OrderItem item = new OrderItem(product, 1);

        order.addItem(item);

        assertEquals(1, order.getItemCount());
    }

    @Test
    void shouldCountQuantitiesAcrossOrderItems() {
        Order order = new Order(OrderId.newId());
        ProductCode code = new ProductCode("  KB-001  ");

        Product keyboard =
                new Product(code,
                        "Keyboard",
                        new BigDecimal("50.00")
                );

        Product mouse =
                new Product(code,
                        "Mouse",
                        new BigDecimal("20.00")
                );

        order.addItem(new OrderItem(keyboard, 2));
        order.addItem(new OrderItem(mouse, 3));

        assertEquals(5, order.getItemCount());
    }

    @Test
    void shouldCalculateSubtotalForMultipleItems() {
        Order order = new Order(OrderId.newId());
        ProductCode code = new ProductCode("  KB-001  ");
        ProductCode code2 = new ProductCode("  M-001  ");
        Product keyboard = new Product(code,"Keyboard", new BigDecimal("50.00")); // 2 * $50 = $100
        Product mouse = new Product(code2,"Mouse", new BigDecimal("20.00"));       // 3 * $20 = $60

        order.addItem(new OrderItem(keyboard, 2));
        order.addItem(new OrderItem(mouse, 3));

        BigDecimal expectedSubtotal = new BigDecimal("160.00");
        assertEquals(0, expectedSubtotal.compareTo(order.subtotal()));
    }

    @Test
    void shouldRejectNullOrderItem() {
        Order order = new Order(OrderId.newId());

        assertThrows(
                IllegalArgumentException.class,
                () -> order.addItem(null)
        );
    }

    @Test
    void shouldCalculateTotalWithZeroDiscount() {
        Order order = new Order(OrderId.newId());
        ProductCode code = new ProductCode("  KB-001  ");
        Product product = new Product(code,"Keyboard", new BigDecimal("100.00"));
        order.addItem(new OrderItem(product, 1));

        BigDecimal total = order.totalAfterDiscount(BigDecimal.ZERO);

        assertEquals(0, new BigDecimal("100.00").compareTo(total));
    }

    @Test
    void shouldCalculateTotalWithTenPercentDiscount() {
        Order order = new Order(OrderId.newId());
        ProductCode code = new ProductCode("  KB-001  ");
        Product product = new Product(code,"Keyboard", new BigDecimal("100.00"));
        order.addItem(new OrderItem(product, 1));

        BigDecimal total = order.totalAfterDiscount(new BigDecimal("10"));

        assertEquals(0, new BigDecimal("90.00").compareTo(total));
    }

    @Test
    void shouldCalculateTotalWithOneHundredPercentDiscount() {
        Order order = new Order(OrderId.newId());
        ProductCode code = new ProductCode("  KB-001  ");
        Product product = new Product(code,"Keyboard", new BigDecimal("100.00"));
        order.addItem(new OrderItem(product, 1));

        BigDecimal total = order.totalAfterDiscount(new BigDecimal("100"));

        assertEquals(0, BigDecimal.ZERO.compareTo(total));
    }

    // ==========================================
    // Discount Validation Tests
    // ==========================================

    @Test
    void shouldRejectNullDiscount() {
        Order order = new Order(OrderId.newId());

        assertThrows(
                IllegalArgumentException.class,
                () -> order.totalAfterDiscount(null)
        );
    }

    @Test
    void shouldRejectNegativeDiscount() {
        Order order = new Order(OrderId.newId());

        assertThrows(
                IllegalArgumentException.class,
                () -> order.totalAfterDiscount(new BigDecimal("-1.00"))
        );
    }

    @Test
    void shouldRejectDiscountAboveOneHundred() {
        Order order = new Order(OrderId.newId());

        assertThrows(
                IllegalArgumentException.class,
                () -> order.totalAfterDiscount(new BigDecimal("100.01"))
        );
    }

    @Test
    void shouldNotExposeMutableItemsList() {
        Order order = new Order(OrderId.newId());
        ProductCode code = new ProductCode("  KB-001  ");
        Product product = new Product(code,"Keyboard", new BigDecimal("50.00"));
        order.addItem(new OrderItem(product, 2));

       assertEquals(2, order.getItemCount());
    }

    @Test
    void newOrderShouldBeDraft() {
        Order order =
                new Order(OrderId.newId());

        assertEquals(
                OrderStatus.DRAFT,
                order.getStatus()
        );
    }

    @Test
    void shouldConfirmOrderWithItems() {
        Order order =
                new Order(OrderId.newId());

        Product product =
                new Product(
                        new ProductCode("KB-001"),
                        "Keyboard",
                        new BigDecimal("79.90")
                );

        order.addItem(
                new OrderItem(product, 1)
        );

        order.confirm();

        assertEquals(
                OrderStatus.CONFIRMED,
                order.getStatus()
        );
    }

    @Test
    void shouldRejectConfirmingEmptyOrder() {
        Order order =
                new Order(OrderId.newId());

        assertThrows(
                IllegalStateException.class,
                order::confirm // same as () -> order.confirm()
        );
    }

    @Test
    void shouldRejectConfirmingConfirmedOrder() {
        Order order =
                createOrderWithItem();

        order.confirm();

        assertThrows(
                IllegalStateException.class,
                order::confirm
        );
    }

    @Test
    void shouldPayConfirmedOrder() {
        Order order =
                createOrderWithItem();

        order.confirm();
        order.pay();

        assertEquals(
                OrderStatus.PAID,
                order.getStatus()
        );
    }

    @Test
    void shouldRejectPayingDraftOrder() {
        Order order =
                createOrderWithItem();

        assertThrows(
                IllegalStateException.class,
                order::pay
        );
    }

    @Test
    void shouldShipPaidOrder() {
        Order order =
                createOrderWithItem();

        order.confirm();
        order.pay();
        order.ship();

        assertEquals(
                OrderStatus.SHIPPED,
                order.getStatus()
        );
    }

    @Test
    void shouldRejectShippingConfirmedOrder() {
        Order order =
                createOrderWithItem();

        order.confirm();

        assertThrows(
                IllegalStateException.class,
                order::ship
        );
    }

    @Test
    void shouldCancelDraftOrder() {
        Order order =
                new Order(OrderId.newId());

        order.cancel();

        assertEquals(
                OrderStatus.CANCELLED,
                order.getStatus()
        );
    }

    @Test
    void shouldCancelConfirmedOrder() {
        Order order =
                createOrderWithItem();

        order.confirm();
        order.cancel();

        assertEquals(
                OrderStatus.CANCELLED,
                order.getStatus()
        );
    }
    @Test
    void shouldCancelPaidOrder() {
        Order order =
                createOrderWithItem();

        order.confirm();
        order.pay();
        order.cancel();

        assertEquals(
                OrderStatus.CANCELLED,
                order.getStatus()
        );
    }

    @Test
    void shouldRejectCancellingShippedOrder() {
        Order order =
                createOrderWithItem();

        order.confirm();
        order.pay();
        order.ship();

        assertThrows(
                IllegalStateException.class,
                order::cancel
        );
    }

    @Test
    void shouldRejectCancellingCancelledOrder() {
        Order order =
                new Order(OrderId.newId());

        order.cancel();

        assertThrows(
                IllegalStateException.class,
                order::cancel
        );
    }

    private Order createOrderWithItem() {
        Product product =
                new Product(
                        new ProductCode("KB-001"),
                        "Keyboard",
                        new BigDecimal("79.90")
                );

        Order order =
                new Order(OrderId.newId());

        order.addItem(
                new OrderItem(product, 1)
        );

        return order;
    }

    @Test
    void shouldRejectAddingItemToConfirmedOrder() {
        Order order =
                createOrderWithItem();

        order.confirm();

        Product mouse =
                new Product(
                        new ProductCode("MS-001"),
                        "Mouse",
                        new BigDecimal("29.90")
                );

        OrderItem mouseItem =
                new OrderItem(mouse, 1);

        assertThrows(
                IllegalStateException.class,
                () -> order.addItem(mouseItem)
        );
    }

}
