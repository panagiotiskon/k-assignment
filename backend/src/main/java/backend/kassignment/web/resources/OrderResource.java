package backend.kassignment.web.resources;

import java.util.List;

public class OrderResource {

    private Long id;
    private Long userId;
    private List<OrderItemResource> orderItems;
    private Long totalAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderItemResource> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemResource> orderItems) {
        this.orderItems = orderItems;
    }
}
