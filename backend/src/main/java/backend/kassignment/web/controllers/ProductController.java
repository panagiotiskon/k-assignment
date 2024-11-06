package backend.kassignment.web.controllers;

import backend.kassignment.service.ProductService;
import backend.kassignment.web.requests.ProductCreateUpdateRequest;
import backend.kassignment.web.requests.ProductPatchRequest;
import backend.kassignment.web.requests.ProductSearchFilter;
import backend.kassignment.web.resources.ProductResource;
import backend.kassignment.web.validators.ProductPatchValidator;
import backend.kassignment.web.validators.ProductValidator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("app/products")
public class ProductController {


    private final ProductService productService;
    private final ProductValidator productValidator;
    private final ProductPatchValidator productPatchValidator;

    public ProductController(ProductService productService, ProductValidator productValidator, ProductPatchValidator productPatchValidator) {
        this.productService = productService;
        this.productValidator = productValidator;
        this.productPatchValidator = productPatchValidator;
    }

    @InitBinder("productCreateUpdateRequest")
    public void initCreateUpdateBinder(WebDataBinder binder) {
        binder.setValidator(productValidator);
    }

    @InitBinder("productPatchRequest")
    public void initPatchBinder(WebDataBinder binder) {
        binder.setValidator(productPatchValidator);
    }
    @GetMapping()
    public ResponseEntity<Page<ProductResource>> getAllProducts(@ModelAttribute ProductSearchFilter productSearchFilterList,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Page<ProductResource> productResourceList = productService.getAllProducts(productSearchFilterList, page, size);
        return ResponseEntity.ok(productResourceList);
    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResource getProduct(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductResource createProduct(@RequestBody @Validated ProductCreateUpdateRequest productCreateUpdateRequest) {
        return productService.createProduct(productCreateUpdateRequest);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public ProductResource updateProduct(@PathVariable Long productId,
                                         @RequestBody @Validated ProductCreateUpdateRequest productCreateUpdateRequest) {
        return productService.updateProduct(productId, productCreateUpdateRequest);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{productId}")
    public ProductResource patchProduct(@PathVariable Long productId,
                                        @RequestBody @Validated ProductPatchRequest productPatchRequest) {
        return productService.patchProduct(productId, productPatchRequest);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<String>> getAllProductCompanies() {
        List<String> productCompanies = productService.getAllProductCompanies();
        return ResponseEntity.ok(productCompanies);
    }

}
