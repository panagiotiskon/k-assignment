package backend.kassignment.web.mappers;

import backend.kassignment.domain.Product;
import backend.kassignment.web.requests.ProductCreateUpdateRequest;
import backend.kassignment.web.requests.ProductPatchRequest;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.Instant;
import java.util.UUID;

@Component
public class ProductMapper {

    public Product mapToProduct(ProductCreateUpdateRequest request) {
        Product product = new Product();
        product.setSku(generateUUIDNo());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCompany(request.getCompany());
        product.setCreatedAt(Instant.now());
        product.setUpdatedAt(Instant.now());
        return product;
    }

    public Product updateProduct(Product product, ProductCreateUpdateRequest request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCompany(request.getCompany());
        product.setUpdatedAt(Instant.now());
        return product;
    }

    public Product patchProduct(Product product, ProductPatchRequest request) {
        if(request.getName()!=null)
            product.setName(request.getName());
        if(request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if(request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if(request.getCompany() != null) {
            product.setCompany(request.getCompany());
        }
        product.setUpdatedAt(Instant.now());
        return product;
    }


    // Generates a random UUID and transforms it to a long number

    private Long generateUUIDNo(){
        String uuidString = UUID.randomUUID().toString();
        BigInteger uuidBigInt = new BigInteger(uuidString.replace("-", ""), 16);
        return uuidBigInt.mod(BigInteger.valueOf(Long.MAX_VALUE)).longValue();
    }

}
