package backend.kassignment.web.controllers;

import backend.kassignment.service.ProductService;
import backend.kassignment.web.requests.ProductSearchFilter;
import backend.kassignment.web.resources.ProductResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("auth/products")
public class ProductController {


    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @RequestMapping("")
    public ResponseEntity<Page<ProductResource>> getAllProducts(@ModelAttribute ProductSearchFilter productSearchFilterList,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Page<ProductResource> productResourceList = productService.getAllProducts(productSearchFilterList, page, size);
        return ResponseEntity.ok(productResourceList);
    }


}
