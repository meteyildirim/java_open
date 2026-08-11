import com.mete.roadmap.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private ProductRepository repository;
    private ProductService service;

    @BeforeEach
    void setUp() {
        // 1. Initialize repository to prevent NullPointerException
        repository = new InMemoryProductRepository();
        service = new ProductService(repository);
    }

    @Test
    void shouldRegisterProduct() {
        service.registerProduct("KB-001", "Keyboard", new BigDecimal("80"));

        Optional<Product> foundProduct = repository.findByCode(new ProductCode("KB-001"));

        assertTrue(foundProduct.isPresent());

        // 2. Compare ProductCode object directly (or use .getCode().value())
        assertEquals(new ProductCode("KB-001"), foundProduct.orElseThrow().getCode());
    }

    @Test
    void shouldRejectDuplicateProduct() {
        service.registerProduct("KB-001", "Keyboard", new BigDecimal("80"));
        assertThrows(DuplicateProductException.class,()-> service.registerProduct("KB-001", "Keyboard", new BigDecimal("80")) );

    }


    @Test
    void shouldGetExistingProduct() {
        service.registerProduct("KB-001", "Keyboard", new BigDecimal("80"));
        Optional<Product> foundProduct = repository.findByCode(new ProductCode("KB-001"));
        assertTrue(foundProduct.isPresent());

    }
    @Test
    void shouldReturnEmptyWhenProductDoesNotExist() {
        Optional<Product> foundProduct = repository.findByCode(new ProductCode("KB-001"));

        assertTrue(foundProduct.isEmpty());
    }

    @Test
    void shouldReturnAllProducts() {
        // 1. Register multiple products
        service.registerProduct("KB-001", "Keyboard", new BigDecimal("80.00"));
        service.registerProduct("MS-001", "Mouse", new BigDecimal("25.00"));
        service.registerProduct("MN-001", "Monitor", new BigDecimal("200.00"));

        // 2. Retrieve all products from the service
        List<Product> products = repository.findAll();

        // 3. Verify total count
        assertEquals(3, products.size());
    }
}
