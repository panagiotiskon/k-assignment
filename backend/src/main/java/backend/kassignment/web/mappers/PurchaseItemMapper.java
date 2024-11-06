package backend.kassignment.web.mappers;

import backend.kassignment.domain.Purchase;
import backend.kassignment.domain.PurchaseItem;
import backend.kassignment.domain.Product;
import backend.kassignment.web.requests.PurchaseItemCreateRequest;
import backend.kassignment.web.resources.PurchaseItemResource;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class PurchaseItemMapper {

    public PurchaseItem mapToPurchaseItem(PurchaseItemCreateRequest purchaseItemCreateRequest, Purchase purchase, Product product) {
        PurchaseItem purchaseItem = new PurchaseItem();
        purchaseItem.setPurchase(purchase);
        purchaseItem.setProduct(product);
        purchaseItem.setQuantity(purchaseItemCreateRequest.getQuantity());
        purchaseItem.setCreatedAt(Instant.now());
        purchaseItem.setUpdatedAt(Instant.now());
        return purchaseItem;
    }

    public PurchaseItemResource mapToPurchaseItemResource(PurchaseItem purchaseItem) {
        PurchaseItemResource purchaseItemResource = new PurchaseItemResource();
        purchaseItemResource.setId(purchaseItem.getId());
        purchaseItemResource.setProductId(purchaseItem.getProduct().getId());
        purchaseItemResource.setQuantity(purchaseItem.getQuantity());
        return purchaseItemResource;
    }

}
