package backend.kassignment.web.validators;

import backend.kassignment.web.requests.ProductCreateUpdateRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return ProductCreateUpdateRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductCreateUpdateRequest productCreateUpdateRequest = (ProductCreateUpdateRequest) target;
        if (errors.hasErrors()) {
            return;
        }
        if (productCreateUpdateRequest.getName().isBlank()) {
            errors.rejectValue("name", "required");
        }
        if (productCreateUpdateRequest.getCompany().isBlank()) {
            errors.rejectValue("company", "required");
        }
        if (productCreateUpdateRequest.getPrice()==null) {
            errors.rejectValue("price", "required");
        }
    }
}
