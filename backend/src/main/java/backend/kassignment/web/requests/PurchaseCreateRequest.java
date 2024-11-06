package backend.kassignment.web.requests;

import java.util.List;

public class PurchaseCreateRequest {

    List<PurchaseItemCreateRequest> itemsToPurchase;

    public List<PurchaseItemCreateRequest> getItemsToPurchase() {
        return itemsToPurchase;
    }

    public void setItemsToPurchase(List<PurchaseItemCreateRequest> itemsToPurchase) {
        this.itemsToPurchase = itemsToPurchase;
    }
}
