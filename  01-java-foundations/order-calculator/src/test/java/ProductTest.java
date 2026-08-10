import com.mete.roadmap.order.Product;

import java.math.BigDecimal;

import com.mete.roadmap.order.ProductCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test
    void shouldCreateProductWithCode() {
        ProductCode code = new ProductCode("KB-001");
        Product product = new Product(
                code,
                "Mechanical Keyboard",
                new BigDecimal("79.90")
        );

        assertEquals(code, product.getCode());
        assertEquals("Mechanical Keyboard", product.getName());
        assertEquals(new BigDecimal("79.90"), product.getUnitPrice());
    }

    @Test
    void shouldRejectNullProductCode() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(null, "Keyboard", new BigDecimal("79.90"))
        );
    }

    @Test
    void shouldRejectNullName() {
        ProductCode code = new ProductCode("KB-001");
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(code, null, new BigDecimal("79.90"))
        );
    }

    @Test
    void shouldRejectBlankName() {
        ProductCode code = new ProductCode("KB-001");
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(code, "  ", new BigDecimal("79.90"))
        );
    }

    @Test
    void shouldRejectInvalidUnitPrice() {
        ProductCode code = new ProductCode("KB-001");
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(code, "Keyboard", BigDecimal.ZERO)
        );
    }
}