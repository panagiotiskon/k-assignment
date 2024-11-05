package backend.kassignment.web.validators;

import backend.kassignment.web.requests.OrderCreateRequest;
import backend.kassignment.web.requests.OrderItemCreateRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class OrderValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return OrderCreateRequest.class.equals(clazz);
    }
    @Override
    public void validate(Object target, Errors errors){
         OrderCreateRequest orderCreateRequest = (OrderCreateRequest) target;
        if (errors.hasErrors()) {
            return;
        }
        List<OrderItemCreateRequest> orderItems = orderCreateRequest.getOrderItems();
        for(OrderItemCreateRequest item : orderItems){
            if(item.getQuantity()==0 || item.getQuantity()==null){
                errors.rejectValue("orderItem", "Quantity is required");
            }
        }

    }
}
