package backend.kassignment.service;

import backend.kassignment.domain.Purchase;
import backend.kassignment.domain.PurchaseItem;
import backend.kassignment.domain.Product;
import backend.kassignment.domain.User;
import backend.kassignment.domain.exceptions.ProductNotFoundException;
import backend.kassignment.domain.exceptions.UserNotFoundException;
import backend.kassignment.domain.repository.PurchaseItemRepository;
import backend.kassignment.domain.repository.PurchaseRepository;
import backend.kassignment.domain.repository.ProductRepository;
import backend.kassignment.domain.repository.UserRepository;
import backend.kassignment.web.mappers.PurchaseItemMapper;
import backend.kassignment.web.mappers.PurchaseMapper;
import backend.kassignment.web.requests.PurchaseCreateRequest;
import backend.kassignment.web.requests.PurchaseItemCreateRequest;
import backend.kassignment.web.resources.PurchaseItemResource;
import backend.kassignment.web.resources.PurchaseResource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final UserService userService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final PurchaseMapper purchaseMapper;
    private final UserRepository userRepository;
    private final PurchaseItemMapper purchaseItemMapper;

    public PurchaseService(PurchaseRepository purchaseRepository,
                           PurchaseItemRepository purchaseItemRepository,
                           UserService userService,
                           ProductService productService, ProductRepository productRepository, PurchaseMapper purchaseMapper, UserRepository userRepository, PurchaseItemMapper purchaseItemMapper) {
        this.purchaseRepository = purchaseRepository;
        this.purchaseItemRepository = purchaseItemRepository;
        this.userService = userService;
        this.productService = productService;
        this.productRepository = productRepository;
        this.purchaseMapper = purchaseMapper;
        this.userRepository = userRepository;
        this.purchaseItemMapper = purchaseItemMapper;
    }

    @Transactional
    public PurchaseResource createPurchase(Long userId, PurchaseCreateRequest purchaseCreateRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        // Extract all product ids from purchase items

        List<Long> productIds = purchaseCreateRequest.getItemsToPurchase().stream()
                .map(PurchaseItemCreateRequest::getProductId)
                .toList();

        // fetch all products in one query and map them in each product id

        Map<Long, Product> products = productRepository.findAllById(productIds)
                .stream().collect(Collectors.toMap(Product::getId, product -> product));

        Purchase purchase = purchaseMapper.mapToPurchase(user);

        // create purchase items from products

        List<PurchaseItem> purchaseItems = purchaseCreateRequest.getItemsToPurchase().stream()
                .map(purchaseItemCreateRequest -> {
                    Product product = products.get(purchaseItemCreateRequest.getProductId());
                    if (product == null) {
                        throw new ProductNotFoundException();
                    }
                    return purchaseItemMapper.mapToPurchaseItem(purchaseItemCreateRequest, purchase, product);
                }).toList();

        // find total amount of the purchase

        double totalAmount = purchaseItems.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        purchase.setTotalAmount(totalAmount);
        purchase.setPurchaseItems(purchaseItems);
        // save purchase and purchase items

        purchaseRepository.save(purchase);

        List<PurchaseItemResource> purchaseItemResourceList = purchaseItems.stream()
                .map(purchaseItemMapper::mapToPurchaseItemResource)
                .toList();

        return purchaseMapper.mapToPurchaseResource(purchase, purchaseItemResourceList);
    }
}
