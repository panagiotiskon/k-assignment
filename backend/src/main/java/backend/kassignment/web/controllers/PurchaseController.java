package backend.kassignment.web.controllers;

import backend.kassignment.service.PurchaseService;
import backend.kassignment.web.requests.PurchaseCreateRequest;
import backend.kassignment.web.resources.PurchaseResource;
import backend.kassignment.web.validators.PurchaseValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app/{userId}/purchases")
public class PurchaseController {

//    @GetMapping("/{userId}")
//    public Page<> getOrders(@PathVariable Long userId, Pageable pageable) {
//
//    }

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @InitBinder
    public void binder(WebDataBinder webDataBinder) {
        webDataBinder.setValidator(new PurchaseValidator());
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @ResponseStatus(HttpStatus.CREATED)
    public PurchaseResource createPurchase(@PathVariable Long userId, @RequestBody @Validated PurchaseCreateRequest purchaseCreateRequest) {
        return purchaseService.createPurchase(userId, purchaseCreateRequest);
    }

//    @DeleteMapping
//    @PreAuthorize("hasRole('ROLE_CLIENT')")
//    @ResponseStatus(HttpStatus.OK)

}
