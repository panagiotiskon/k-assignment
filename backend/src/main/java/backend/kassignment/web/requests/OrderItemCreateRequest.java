package backend.kassignment.web.requests;

import jakarta.validation.constraints.NotNull;

public class OrderItemCreateRequest {

    @NotNull
    Long productId;
    @NotNull
    Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
