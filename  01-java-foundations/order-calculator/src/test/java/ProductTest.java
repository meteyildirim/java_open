import com.mete.roadmap.order.Product;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ProductTest {
    @Test
    void shouldCreateValidProduct() {
        Product product = new Product(
                "Mechanical Keyboard",
                new BigDecimal("79.90")
        );

        assertEquals(
                "Mechanical Keyboard",
                product.getName()
        );

        assertEquals(
                new BigDecimal("79.90"),
                product.getUnitPrice()
        );
    }
}
