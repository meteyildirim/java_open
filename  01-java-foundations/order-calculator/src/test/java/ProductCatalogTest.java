

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.mete.roadmap.order.Product;
import com.mete.roadmap.order.ProductCatalog;
import com.mete.roadmap.order.ProductCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductCatalogTest {

    @Test
    void newCatalogShouldBeEmpty() {
        ProductCatalog catalog = new ProductCatalog();
        assertEquals(0, catalog.size());
    }

    @Test
    void shouldAddProduct() {
        ProductCatalog catalog = new ProductCatalog();
        Product product = new Product(new ProductCode("KB-001"), "Keyboard", new BigDecimal("50.00"));

        catalog.addProduct(product);

        assertEquals(1, catalog.size());
    }

    @Test
    void shouldRejectNullProduct() {
        ProductCatalog catalog = new ProductCatalog();
        assertThrows(
                IllegalArgumentException.class,
                () -> catalog.addProduct(null)
        );
    }

    @Test
    void shouldRejectDuplicateProductCode() {
        ProductCatalog catalog = new ProductCatalog();
        Product p1 = new Product(new ProductCode("KB-001"), "Keyboard 1", new BigDecimal("50.00"));
        Product p2 = new Product(new ProductCode("KB-001"), "Keyboard 2", new BigDecimal("60.00"));

        catalog.addProduct(p1);

        assertThrows(
                IllegalArgumentException.class,
                () -> catalog.addProduct(p2)
        );
    }

    @Test
    void shouldFindProductByCode() {
        ProductCatalog catalog = new ProductCatalog();
        Product product = new Product(new ProductCode("KB-001"), "Keyboard", new BigDecimal("50.00"));
        catalog.addProduct(product);

        Optional<Product> result = catalog.findByCode(new ProductCode("KB-001"));

        assertEquals(product, result.orElseThrow());
    }

    @Test
    void shouldReturnEmptyOptionalWhenProductDoesNotExist() {
        ProductCatalog catalog = new ProductCatalog();

        Optional<Product> result = catalog.findByCode(new ProductCode("KB-001"));

        assertEquals(Optional.empty(), result);
    }

    @Test
    void shouldReturnAllProducts() {
        ProductCatalog catalog = new ProductCatalog();
        Product p1 = new Product(new ProductCode("KB-001"), "Keyboard", new BigDecimal("50.00"));
        Product p2 = new Product(new ProductCode("MS-001"), "Mouse", new BigDecimal("20.00"));

        catalog.addProduct(p1);
        catalog.addProduct(p2);

        List<Product> allProducts = catalog.getProducts();

        assertEquals(2, allProducts.size());
    }

    @Test
    void shouldReturnProductCodes() {
        ProductCatalog catalog = new ProductCatalog();
        Product p1 = new Product(new ProductCode("KB-001"), "Keyboard", new BigDecimal("50.00"));
        catalog.addProduct(p1);

        Set<ProductCode> codes = catalog.getProductCodes();

        assertEquals(1, codes.size());
    }

    @Test
    void shouldNotAllowProductCodeCollectionToModifyCatalog() {
        ProductCatalog catalog = new ProductCatalog();
        Product p1 = new Product(new ProductCode("KB-001"), "Keyboard", new BigDecimal("50.00"));
        catalog.addProduct(p1);

        Set<ProductCode> codes = catalog.getProductCodes();

        assertThrows(
                UnsupportedOperationException.class,
                () -> codes.add(new ProductCode("MS-001"))
        );
    }

    @Test
    void shouldSearchProductsByName() {
        ProductCatalog catalog = new ProductCatalog();
        Product p1 = new Product(new ProductCode("KB-001"), "Mechanical Keyboard", new BigDecimal("80.00"));
        Product p2 = new Product(new ProductCode("KB-002"), "Wireless Keyboard", new BigDecimal("90.00"));
        Product p3 = new Product(new ProductCode("MS-001"), "Gaming Mouse", new BigDecimal("40.00"));

        catalog.addProduct(p1);
        catalog.addProduct(p2);
        catalog.addProduct(p3);

        List<Product> results = catalog.searchByName("keyboard");

        assertEquals(2, results.size());
    }
}