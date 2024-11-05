package backend.kassignment.service;

import backend.kassignment.domain.Product;
import backend.kassignment.domain.exceptions.ProductNotFoundException;
import backend.kassignment.domain.repository.ProductRepository;
import backend.kassignment.domain.repository.specifications.ProductSpecifications;
import backend.kassignment.web.mappers.ProductMapper;
import backend.kassignment.web.requests.ProductCreateUpdateRequest;
import backend.kassignment.web.requests.ProductPatchRequest;
import backend.kassignment.web.requests.ProductSearchFilter;
import backend.kassignment.web.resources.ProductResource;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper = new ProductMapper();

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    /**
     * This method returns a paginated and filtered list of products based on the given search filters.
     **
     * @param productSearchFilter This filter includes criteria for filtering by product companies and price range.
     * @param page                The requested page number (1-based index).
     * @param size                The number of products per page.
     * @return A {@link Page} containing {@link ProductResource} objects representing the paginated and filtered products.
     */

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

    /**
     * This method returns information about a specified product
     *
     * @param id the id of the {@link Product}
     * @return a {@link ProductResource} containing the information of the product
     */

    public ProductResource getProductById(Long id) {
        return new ProductResource(productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new));
    }


    /**
     * This method creates a new product
     *
     * @param productCreateUpdateRequest containing all the necessary information
     * @return A {@link ProductResource} containing information of the newly created {@link Product}
     */

    @Transactional
    public ProductResource createProduct(ProductCreateUpdateRequest productCreateUpdateRequest) {
        Product product = productMapper.mapToProduct(productCreateUpdateRequest);
        productRepository.save(product);
        return new ProductResource(product);
    }

    /**
     * This method updates a specified product
     *
     * @param id                         the id of the product to be modified
     * @param productCreateUpdateRequest containing all the changes to be made
     * @return A {@link ProductResource} containing information of the modified {@link Product}
     */

    @Transactional
    public ProductResource updateProduct(Long id, ProductCreateUpdateRequest productCreateUpdateRequest) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        Product updatedProduct = productMapper.updateProduct(product, productCreateUpdateRequest);
        productRepository.save(updatedProduct);
        return new ProductResource(updatedProduct);
    }

    /**
     * This method patches a specified product
     *
     * @param id                  the id of the product to be modified
     * @param productPatchRequest containing all the changes to be made
     * @return A {@link ProductResource} containing information of the modified {@link Product}
     */

    @Transactional
    public ProductResource patchProduct(Long id, ProductPatchRequest productPatchRequest) {
        Product product = productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        Product updatedProduct = productMapper.patchProduct(product, productPatchRequest);
        productRepository.save(updatedProduct);
        return new ProductResource(updatedProduct);
    }

    /**
     * This method deletes a specified product
     *
     * @param id the id of the product to be deleted
     */
    @Transactional
    public void deleteProductById(Long id) {
        productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
        productRepository.deleteById(id);
    }

    /**
     * This method returns a list of strings containing the names of all product companies.
     *
     * @return A {@link List} of all unique company names associated with the products.
     */
    public List<String> getAllProductCompanies() {
        return productRepository
                .findAll()
                .stream()
                .map(Product::getCompany)
                .distinct()
                .toList();
    }

}
