
import com.mete.roadmap.order.ProductCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductCodeTest {

    @Test
    void shouldCreateValidCode() {
        ProductCode code = new ProductCode("KB-001");
        assertEquals("KB-001", code.value());
    }

    @Test
    void shouldRejectNullCode() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductCode(null)
        );
    }

    @Test
    void shouldRejectBlankCode() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ProductCode("   ")
        );
    }

    @Test
    void shouldTrimCode() {
        ProductCode code = new ProductCode("  KB-001  ");
        assertEquals("KB-001", code.value());
    }

    @Test
    void shouldConvertCodeToUppercase() {
        ProductCode code = new ProductCode("kb-001");
        assertEquals("KB-001", code.value());
    }

    @Test
    void shouldConsiderEqualCodesEqual() {
        ProductCode first = new ProductCode("KB-001");
        ProductCode second = new ProductCode("kb-001");
        assertEquals(first, second);
    }

    @Test
    void shouldProduceEqualHashCodeForEqualCodes() {
        ProductCode first = new ProductCode("KB-001");
        ProductCode second = new ProductCode("kb-001");
        assertEquals(first.hashCode(), second.hashCode());
    }
}
