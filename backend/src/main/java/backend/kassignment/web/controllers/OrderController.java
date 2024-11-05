package backend.kassignment.web.controllers;

import backend.kassignment.service.OrderService;
import backend.kassignment.web.requests.OrderCreateRequest;
import backend.kassignment.web.resources.OrderResource;
import backend.kassignment.web.validators.OrderValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app/orders")
public class OrderController {

//    @GetMapping("/{userId}")
//    public Page<> getOrders(@PathVariable Long userId, Pageable pageable) {
//
//    }

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @InitBinder
    public void binder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new OrderValidator());
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @ResponseStatus(HttpStatus.OK)
    public OrderResource createOrder(@RequestBody @Validated OrderCreateRequest orderCreateRequest) {
        return orderService.createOrder(orderCreateRequest);
    }

}
