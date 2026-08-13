package com.mete.roadmap.order;

public final class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository
    ) {
        if (orderRepository == null) {
            throw new IllegalArgumentException(
                    "Order repository cannot be null"
            );
        }

        if (productRepository == null) {
            throw new IllegalArgumentException(
                    "Product repository cannot be null"
            );
        }

        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order createOrder() {
        Order order =
                new Order(OrderId.newId());

        orderRepository.save(order);

        return order;
    }

    public void addProductToOrder(
            OrderId orderId,
            ProductCode productCode,
            int quantity
    ) {
        Order order =
                getExistingOrder(orderId);

        Product product =
                productRepository
                        .findByCode(productCode)
                        .orElseThrow(
                                () -> new ProductNotFoundException(
                                        productCode
                                )
                        );

        OrderItem item =
                new OrderItem(
                        product,
                        quantity
                );

        order.addItem(item);

        orderRepository.save(order);
    }

    public void confirmOrder(OrderId orderId) {
        Order order =
                getExistingOrder(orderId);

        order.confirm();

        orderRepository.save(order);
    }

    public void payOrder(OrderId orderId) {
        Order order =
                getExistingOrder(orderId);

        order.pay();

        orderRepository.save(order);
    }

    public void shipOrder(OrderId orderId) {
        Order order =
                getExistingOrder(orderId);

        order.ship();

        orderRepository.save(order);
    }

    private Order getExistingOrder(OrderId orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(
                        () -> new OrderNotFoundException(
                                orderId
                        )
                );
    }
}