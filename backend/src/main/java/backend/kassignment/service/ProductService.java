package backend.kassignment.service;

import backend.kassignment.domain.Product;
import backend.kassignment.domain.repository.ProductRepository;
import backend.kassignment.domain.repository.specifications.ProductSpecifications;
import backend.kassignment.web.requests.ProductSearchFilter;
import backend.kassignment.web.resources.ProductResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Page<ProductResource> getAllProducts(List<ProductSearchFilter> productSearchFilters, int page, int size) {
        var specification = ProductSpecifications.searchFilterSpecification(productSearchFilters);
        if(page<1){
            page = 1;
        }
        if(size<1){
            size= 10;
        }
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Product> productPage = productRepository.findAll(specification, pageable);
        return productPage.map(ProductResource::new);
    }

}
