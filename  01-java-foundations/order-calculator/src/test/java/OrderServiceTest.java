
import com.mete.roadmap.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository =
                new InMemoryOrderRepository();

        productRepository =
                new InMemoryProductRepository();

        orderService =
                new OrderService(
                        orderRepository,
                        productRepository
                );
    }

    @Test
    void shouldCreateOrder() {
        Order order =
                orderService.createOrder();

        assertEquals(
                OrderStatus.DRAFT,
                order.getStatus()
        );

        assertTrue(
                orderRepository
                        .findById(order.getId())
                        .isPresent()
        );
    }

    @Test
    void shouldAddExistingProductToOrder() {
        Product product =
                createAndSaveKeyboard();

        Order order =
                orderService.createOrder();

        orderService.addProductToOrder(
                order.getId(),
                product.getCode(),
                2
        );

        Order savedOrder =
                orderRepository
                        .findById(order.getId())
                        .orElseThrow();

        assertEquals(
                1,
                savedOrder.getLineCount()
        );

        assertEquals(
                2,
                savedOrder.getItemCount()
        );

        assertEquals(
                new BigDecimal("159.80"),
                savedOrder.subtotal()
        );
    }

    @Test
    void shouldRejectUnknownOrder() {
        Product product =
                createAndSaveKeyboard();

        OrderId unknownOrderId =
                OrderId.newId();

        assertThrows(
                OrderNotFoundException.class,
                () -> orderService.addProductToOrder(
                        unknownOrderId,
                        product.getCode(),
                        1
                )
        );
    }

    @Test
    void shouldRejectUnknownProduct() {
        Order order =
                orderService.createOrder();

        ProductCode unknownProductCode =
                new ProductCode("UNKNOWN");

        assertThrows(
                ProductNotFoundException.class,
                () -> orderService.addProductToOrder(
                        order.getId(),
                        unknownProductCode,
                        1
                )
        );
    }

    @Test
    void shouldConfirmOrder() {
        Order order =
                createOrderWithKeyboard();

        orderService.confirmOrder(
                order.getId()
        );

        Order savedOrder =
                getOrder(order.getId());

        assertEquals(
                OrderStatus.CONFIRMED,
                savedOrder.getStatus()
        );
    }

    @Test
    void shouldPayOrder() {
        Order order =
                createOrderWithKeyboard();

        orderService.confirmOrder(
                order.getId()
        );

        orderService.payOrder(
                order.getId()
        );

        Order savedOrder =
                getOrder(order.getId());

        assertEquals(
                OrderStatus.PAID,
                savedOrder.getStatus()
        );
    }

    @Test
    void shouldShipOrder() {
        Order order =
                createOrderWithKeyboard();

        orderService.confirmOrder(
                order.getId()
        );

        orderService.payOrder(
                order.getId()
        );

        orderService.shipOrder(
                order.getId()
        );

        Order savedOrder =
                getOrder(order.getId());

        assertEquals(
                OrderStatus.SHIPPED,
                savedOrder.getStatus()
        );
    }

    private Product createAndSaveKeyboard() {
        Product product =
                new Product(
                        new ProductCode("KB-001"),
                        "Mechanical Keyboard",
                        new BigDecimal("79.90")
                );

        productRepository.save(product);

        return product;
    }

    private Order createOrderWithKeyboard() {
        Product product =
                createAndSaveKeyboard();

        Order order =
                orderService.createOrder();

        orderService.addProductToOrder(
                order.getId(),
                product.getCode(),
                1
        );

        return order;
    }

    private Order getOrder(OrderId orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow();
    }
}