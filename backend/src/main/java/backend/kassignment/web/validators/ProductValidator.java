package backend.kassignment.web.validators;

import backend.kassignment.web.requests.ProductCreateUpdateRequest;
import backend.kassignment.web.requests.ProductPatchRequest;
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
        String name = productCreateUpdateRequest.getName();
        String description = productCreateUpdateRequest.getDescription();
        String company = productCreateUpdateRequest.getCompany();
        Double price = productCreateUpdateRequest.getPrice();

        if (errors.hasErrors()) {
            return;
        }
        if (name == null || name.isBlank()) {
            errors.rejectValue("name", "required");
        }
        if(description == null || description.isBlank()) {
            errors.rejectValue("description", "required");
        }
        if (company == null || company.isBlank()) {
            errors.rejectValue("company", "required");
        }
        if (price ==null) {
            errors.rejectValue("price", "required");
        }
    }
}
