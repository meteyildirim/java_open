package com.mete.roadmap.order;

public final class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository
    ) {
        this.orderRepository = orderRepository;

        this.productRepository = productRepository;
    }

    public Order createOrder() {
        Order order = new Order(OrderId.newId());

        orderRepository.save(order);

        return order;
    }

    public void addProductToOrder(
            OrderId orderId,
            ProductCode productCode,
            int quantity
    ) {
        Order order =
                orderRepository
                        .findById(orderId)
                        .orElseThrow(
                                () -> new OrderNotFoundException(
                                        orderId
                                )
                        );

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

}