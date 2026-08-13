

import com.mete.roadmap.order.InMemoryOrderRepository;
import com.mete.roadmap.order.Order;
import com.mete.roadmap.order.OrderId;
import com.mete.roadmap.order.OrderRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryOrderRepositoryTest {

    @Test
    void newRepositoryShouldBeEmpty() {
        OrderRepository repository =
                new InMemoryOrderRepository();

        List<Order> orders =
                repository.findAll();

        assertTrue(orders.isEmpty());
    }

    @Test
    void shouldSaveOrder() {
        OrderRepository repository =
                new InMemoryOrderRepository();

        Order order =
                new Order(OrderId.newId());

        repository.save(order);

        assertEquals(
                1,
                repository.findAll().size()
        );
    }

    @Test
    void shouldFindOrderById() {
        OrderRepository repository =
                new InMemoryOrderRepository();

        Order order =
                new Order(OrderId.newId());

        repository.save(order);

        Optional<Order> result =
                repository.findById(
                        order.getId()
                );

        assertTrue(result.isPresent());

        assertEquals(
                order,
                result.get()
        );
    }

    @Test
    void shouldReturnEmptyForUnknownId() {
        OrderRepository repository =
                new InMemoryOrderRepository();

        OrderId unknownId =
                OrderId.newId();

        Optional<Order> result =
                repository.findById(
                        unknownId
                );

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnAllOrders() {
        OrderRepository repository =
                new InMemoryOrderRepository();

        Order first =
                new Order(OrderId.newId());

        Order second =
                new Order(OrderId.newId());

        repository.save(first);
        repository.save(second);

        List<Order> orders =
                repository.findAll();

        assertEquals(
                2,
                orders.size()
        );

        assertTrue(
                orders.contains(first)
        );

        assertTrue(
                orders.contains(second)
        );
    }

    @Test
    void shouldRejectNullOrder() {
        OrderRepository repository =
                new InMemoryOrderRepository();

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(null)
        );
    }

    @Test
    void shouldRejectNullOrderId() {
        OrderRepository repository =
                new InMemoryOrderRepository();

        assertThrows(
                IllegalArgumentException.class,
                () -> repository.findById(null)
        );
    }

    @Test
    void savingSameOrderTwiceShouldNotCreateDuplicate() {
        OrderRepository repository =
                new InMemoryOrderRepository();

        Order order =
                new Order(OrderId.newId());

        repository.save(order);
        repository.save(order);

        assertEquals(
                1,
                repository.findAll().size()
        );
    }

    @Test
    void shouldNotAllowExternalModificationOfOrders() {
        OrderRepository repository =
                new InMemoryOrderRepository();

        repository.save(
                new Order(OrderId.newId())
        );

        List<Order> orders =
                repository.findAll();

        assertThrows(
                UnsupportedOperationException.class,
                orders::clear
        );

        assertEquals(
                1,
                repository.findAll().size()
        );
    }
}