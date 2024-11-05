package backend.kassignment.service;

import backend.kassignment.domain.repository.OrderItemRepository;
import backend.kassignment.domain.repository.OrderRepository;
import backend.kassignment.web.requests.OrderCreateRequest;
import backend.kassignment.web.resources.OrderResource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        UserService userService,
                        ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public OrderResource createOrder(OrderCreateRequest orderCreateRequest) {
        return new OrderResource();
    }
}
