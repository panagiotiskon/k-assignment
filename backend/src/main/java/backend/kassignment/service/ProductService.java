package backend.kassignment.service;

import backend.kassignment.domain.Product;
import backend.kassignment.domain.exceptions.ProductNotFoundException;
import backend.kassignment.domain.repository.ProductRepository;
import backend.kassignment.domain.repository.specifications.ProductSpecifications;
import backend.kassignment.web.mappers.ProductMapper;
import backend.kassignment.web.requests.ProductCreateUpdateRequest;
import backend.kassignment.web.requests.ProductSearchFilter;
import backend.kassignment.web.resources.ProductResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = new ProductMapper();

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Page<ProductResource> getAllProducts(ProductSearchFilter productSearchFilter, int page, int size) {
        var specification = ProductSpecifications.searchFilterSpecification(productSearchFilter);
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> productPage = productRepository.findAll(specification, pageable);
        return productPage.map(ProductResource::new);
    }

    public ProductResource getProductById(Long id) {
        return new ProductResource(productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new));
    }

    public ProductResource createProduct(ProductCreateUpdateRequest productCreateUpdateRequest) {
        Product product = productMapper.mapToProduct(productCreateUpdateRequest);
        productRepository.save(product);
        return new ProductResource(product);
    }

    public void deleteProductById(Long id) {
        productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        productRepository.deleteById(id);
    }

}
