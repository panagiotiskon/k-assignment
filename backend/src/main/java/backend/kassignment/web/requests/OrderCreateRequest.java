package backend.kassignment.web.requests;

import backend.kassignment.domain.OrderItem;

import java.util.List;

public class OrderCreateRequest {

    List<OrderItemCreateRequest> orderItems;

    public List<OrderItemCreateRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemCreateRequest> orderItems) {
        this.orderItems = orderItems;
    }
}
