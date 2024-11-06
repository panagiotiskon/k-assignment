package backend.kassignment.web.validators;

import backend.kassignment.web.requests.PurchaseCreateRequest;
import backend.kassignment.web.requests.PurchaseItemCreateRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class PurchaseValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return PurchaseCreateRequest.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors){
         PurchaseCreateRequest purchaseCreateRequest = (PurchaseCreateRequest) target;
        if (errors.hasErrors()) {
            return;
        }
        List<PurchaseItemCreateRequest> purchaseItems = purchaseCreateRequest.getItemsToPurchase();
        for(PurchaseItemCreateRequest item : purchaseItems){
            if(item.getQuantity()==0 || item.getQuantity()==null){
                errors.rejectValue("PurchaseItem", "Quantity is required");
            }
        }

    }
}
