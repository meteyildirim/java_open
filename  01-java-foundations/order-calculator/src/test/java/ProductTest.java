import com.mete.roadmap.order.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductTest {

    @Test
    public void shouldCreateValidProduct() {
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

    @Test
    public void shouldRejectNullName() {

        assertThrows(IllegalArgumentException.class, ()-> new Product(null, new BigDecimal("11")));

    }

    @Test
    public void shouldRejectEmptyName() {

        assertThrows(IllegalArgumentException.class, ()-> new Product("", new BigDecimal("11")));

    }

    @Test
    public void shouldRejectBlankName() {

        assertThrows(IllegalArgumentException.class, ()-> new Product("    ", new BigDecimal("11")));

    }

    @Test
    public void shouldRejectZeroPrice () {

        assertThrows(IllegalArgumentException.class, ()-> new Product("Keyboard", new BigDecimal(BigInteger.ZERO)));

    }

    @Test
    public void shouldRejectNegativePrice () {

        assertThrows(IllegalArgumentException.class, ()-> new Product("Keyboard", new BigDecimal("-100")));

    }

    @Test
    void shouldRejectNullPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Product(
                        "Keyboard",
                        null
                )
        );
    }
}
