package backend.kassignment.web.resources;

import java.util.List;

public class PurchaseResource {

    private Long id;
    private Long userId;
    private List<PurchaseItemResource> purchasedItems;
    private Double totalAmount;

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

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<PurchaseItemResource> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<PurchaseItemResource> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }
}
