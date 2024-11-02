package backend.kassignment.web.controllers;

import backend.kassignment.service.ProductService;
import backend.kassignment.web.requests.ProductSearchFilter;
import backend.kassignment.web.resources.ProductResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("app/products")
public class ProductController {


    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @RequestMapping("")
    public ResponseEntity<Page<ProductResource>> getAllProducts(@RequestBody(required = false) List<ProductSearchFilter> productSearchFilterList,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        if (productSearchFilterList == null || productSearchFilterList.isEmpty()) {
            productSearchFilterList = Collections.emptyList();
        }
        Page<ProductResource> productResourceList = productService.getAllProducts(productSearchFilterList, page, size);
        return ResponseEntity.ok(productResourceList);
    }


}
