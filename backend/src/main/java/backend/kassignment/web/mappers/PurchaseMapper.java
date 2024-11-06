package backend.kassignment.web.mappers;

import backend.kassignment.domain.Purchase;
import backend.kassignment.domain.User;
import backend.kassignment.web.resources.PurchaseItemResource;
import backend.kassignment.web.resources.PurchaseResource;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class PurchaseMapper {

    public Purchase mapToPurchase(User user) {
        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setCreatedAt(Instant.now());
        purchase.setUpdatedAt(Instant.now());
        return purchase;
    }


    public PurchaseResource mapToPurchaseResource(Purchase purchase, List<PurchaseItemResource> purchaseItemResourceList) {
        PurchaseResource purchaseResource = new PurchaseResource();
        purchaseResource.setId(purchase.getId());
        purchaseResource.setUserId(purchase.getUser().getId());
        purchaseResource.setTotalAmount(purchase.getTotalAmount());
        purchaseResource.setPurchasedItems(purchaseItemResourceList);
        return purchaseResource;
    }

}
