package backend.kassignment.web.controllers;

import backend.kassignment.service.ProductService;
import backend.kassignment.web.requests.ProductCreateUpdateRequest;
import backend.kassignment.web.requests.ProductSearchFilter;
import backend.kassignment.web.resources.ProductResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth/products")
public class ProductController {


    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
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
    public ProductResource createProduct(@RequestBody ProductCreateUpdateRequest productCreateUpdateRequest) {
        return productService.createProduct(productCreateUpdateRequest);
    }

//    @PutMapping("/{productId}")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<ProductResource> updateProduct(@PathVariable Long productId,
//                                                         @RequestBody ProductCreateUpdateRequest productCreateUpdateRequest) {
//
//    }
//
//    @PatchMapping("/{productId}")
//    public ResponseEntity<ProductResource> patchProduct(@PathVariable Long productId,
//                                                            @RequestBody ProductResource productResource) {
//
//    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@PathVariable Long productId) {
        productService.deleteProductById(productId);
    }


}
