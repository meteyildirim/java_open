

import com.mete.roadmap.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductQueryServiceTest {

    private ProductRepository repository;
    private ProductQueryService queryService;

    private Product keyboard;
    private Product mouse;
    private Product monitor;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
        queryService = new ProductQueryService(repository);

        keyboard = new Product(new ProductCode("KB-001"), "Mechanical Keyboard", new BigDecimal("80.00"));
        mouse = new Product(new ProductCode("MS-001"), "Gaming Mouse", new BigDecimal("30.00"));
        monitor = new Product(new ProductCode("MN-001"), "4K Monitor", new BigDecimal("300.00"));
    }

    @Test
    void shouldFindProductsCheaperThanPrice() {
        repository.save(keyboard);
        repository.save(mouse);
        repository.save(monitor);

        List<Product> results = queryService.findProductsCheaperThan(new BigDecimal("100.00"));

        assertEquals(2, results.size());
        assertTrue(results.contains(keyboard));
        assertTrue(results.contains(mouse));
    }

    @Test
    void shouldFindProductsMoreExpensiveThanPrice() {
        repository.save(keyboard);
        repository.save(mouse);
        repository.save(monitor);

        List<Product> results = queryService.findProductsMoreExpensiveThan(new BigDecimal("50.00"));

        assertEquals(2, results.size());
        assertTrue(results.contains(keyboard));
        assertTrue(results.contains(monitor));
    }

    @Test
    void shouldSearchProductsCaseInsensitively() {
        repository.save(keyboard);
        repository.save(mouse);

        List<Product> results = queryService.searchByName("keyBOARD");

        assertEquals(1, results.size());
        assertEquals(keyboard, results.get(0));
    }

    @Test
    void shouldSortProductsByPriceAscending() {
        repository.save(keyboard);
        repository.save(mouse);
        repository.save(monitor);

        List<Product> results = queryService.getProductsSortedByPrice();

        assertEquals(List.of(mouse, keyboard, monitor), results);
    }

    @Test
    void shouldSortProductsByPriceDescending() {
        repository.save(keyboard);
        repository.save(mouse);
        repository.save(monitor);

        List<Product> results = queryService.getProductsSortedByPriceDescending();

        assertEquals(List.of(monitor, keyboard, mouse), results);
    }

    @Test
    void shouldFindCheapestProduct() {
        repository.save(keyboard);
        repository.save(mouse);
        repository.save(monitor);

        Optional<Product> cheapest = queryService.findCheapestProduct();

        assertTrue(cheapest.isPresent());
        assertEquals(mouse, cheapest.get());
    }

    @Test
    void shouldFindMostExpensiveProduct() {
        repository.save(keyboard);
        repository.save(mouse);
        repository.save(monitor);

        Optional<Product> mostExpensive = queryService.findMostExpensiveProduct();

        assertTrue(mostExpensive.isPresent());
        assertEquals(monitor, mostExpensive.get());
    }

    @Test
    void shouldReturnEmptyWhenFindingCheapestInEmptyRepository() {
        Optional<Product> cheapest = queryService.findCheapestProduct();

        assertTrue(cheapest.isEmpty());
    }

    @Test
    void shouldRejectNullPrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> queryService.findProductsCheaperThan(null)
        );
    }

    @Test
    void shouldRejectNegativePrice() {
        assertThrows(
                IllegalArgumentException.class,
                () -> queryService.findProductsCheaperThan(new BigDecimal("-10.00"))
        );
    }

    @Test
    void shouldRejectBlankSearchText() {
        assertThrows(
                IllegalArgumentException.class,
                () -> queryService.searchByName("   ")
        );
    }
}