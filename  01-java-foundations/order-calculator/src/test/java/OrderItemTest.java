import com.mete.roadmap.order.OrderItem;
import com.mete.roadmap.order.Product;
import com.mete.roadmap.order.ProductCode;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderItemTest {

    @Test
    public void shouldCalculateSubtotal() {
        Product product = new Product(
                new ProductCode("KB-001"),
                "Mechanical Keyboard",
                new BigDecimal("79.90")
        );

        OrderItem item = new OrderItem(
                product,
                2
        );

        BigDecimal subtotal = item.subtotal();

        assertEquals(
                new BigDecimal("159.80"),
                subtotal
        );
    }
    @Test
    public void shouldCreateValidOrderItem() {
        Product product = new Product(
                new ProductCode("KB-001"),
                "Mechanical Keyboard",
                new BigDecimal("79.90")
        );

        OrderItem item = new OrderItem(
                product,
                2
        );

        assertEquals("Mechanical Keyboard", item.getProduct().getName());
        assertEquals(new BigDecimal("79.90"), item.getProduct().getUnitPrice());

        }
    @Test
    public void shouldRejectNullProduct () {

        assertThrows(IllegalArgumentException.class, ()-> new OrderItem(null,2) );

    }

    @Test
    public void shouldRejectZeroQuantity () {

        Product product = new Product(
                new ProductCode("KB-001"),
                "Mechanical Keyboard",
                new BigDecimal("79.90")
        );

        assertThrows(IllegalArgumentException.class, ()-> new OrderItem(product,0) );

    }

    @Test
    public void shouldRejectNegativeQuantity () {

        Product product = new Product(
                new ProductCode("KB-001"),
                "Mechanical Keyboard",
                new BigDecimal("79.90")
        );

        assertThrows(IllegalArgumentException.class, ()-> new OrderItem(product,-3) );

    }
}
