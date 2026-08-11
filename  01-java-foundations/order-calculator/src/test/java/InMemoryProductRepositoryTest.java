import com.mete.roadmap.order.InMemoryProductRepository;
import com.mete.roadmap.order.Product;
import com.mete.roadmap.order.ProductCode;
import com.mete.roadmap.order.ProductRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryProductRepositoryTest {

    @Test
    void newRepositoryShouldBeEmpty() {
        ProductRepository repository =
                new InMemoryProductRepository();

        assertEquals(0, repository.findAll().size());
    }

    @Test
    void shouldSaveProduct() {
        ProductRepository repository =
                new InMemoryProductRepository();

        Product product = new Product(
                new ProductCode("KB-001"),
                "Keyboard",
                new BigDecimal("79.90")
        );

        repository.save(product);

        assertTrue(
                repository.existsByCode(
                        new ProductCode("KB-001")
                )
        );
    }

    @Test
    void shouldFindProductByCode() {
        ProductRepository repository =
                new InMemoryProductRepository();

        Product product = new Product(
                new ProductCode("KB-001"),
                "Keyboard",
                new BigDecimal("79.90")
        );

        repository.save(product);

        Optional<Product> result =
                repository.findByCode(
                        new ProductCode("KB-001")
                );

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void shouldReturnEmptyWhenProductDoesNotExist() {
        ProductRepository repository =
                new InMemoryProductRepository();

        Optional<Product> result =
                repository.findByCode(
                        new ProductCode("UNKNOWN")
                );

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnAllProducts() {
        ProductRepository repository = new InMemoryProductRepository();
        for (int i = 0; i < 5; i++) {
            Product product = new Product(
                    new ProductCode("KB-00"+"%s".formatted(i)),
                    "Keyboard",
                    new BigDecimal("79.90")
            );
            repository.save(product);
        }
        assertEquals(5, repository.findAll().size());
    }

    @Test
    void shouldDetectExistingProductCode(){
        ProductRepository repository = new InMemoryProductRepository();
        String productCode = "KB-001";
        Product product = new Product(
                new ProductCode(productCode),
                "Keyboard",
                new BigDecimal("79.90")
        );
        repository.save(product);
        Optional<Product> foundProduct = repository.findByCode(new ProductCode(productCode));

        assertTrue(foundProduct.isPresent());
        assertEquals(new ProductCode("KB-001"), foundProduct.get().getCode());
        assertEquals(product.getName(), foundProduct.get().getName());

    }
}