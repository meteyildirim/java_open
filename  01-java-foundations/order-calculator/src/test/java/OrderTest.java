import com.mete.roadmap.order.Order;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    @Test
    public void newOrderShouldHaveZeroItems () {
        Order order = new Order();
        assertEquals(0, order.getItemCount());

    }

    @Test
    public void newOrderShouldHaveZeroSubtotal () {
        Order order = new Order();
        assertEquals(0, BigDecimal.ZERO.compareTo(order.subtotal()));


    }

}
