package backend.kassignment.web.validators;

import backend.kassignment.web.requests.ProductPatchRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductPatchValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return ProductPatchRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ProductPatchRequest productPatchRequest = (ProductPatchRequest) target;

        String name = productPatchRequest.getName();
        String description = productPatchRequest.getDescription();
        String company = productPatchRequest.getCompany();

        if (errors.hasErrors()) {
            return;
        }
        if (name != null && name.isBlank()) {
            errors.rejectValue("name", "can not be empty");
        }
        if (description != null && description.isBlank()) {
            errors.rejectValue("description", "description can not be empty");
        }

        if (company != null && company.isBlank()) {
            errors.rejectValue("company", "can not be empty");
        }

    }
}
