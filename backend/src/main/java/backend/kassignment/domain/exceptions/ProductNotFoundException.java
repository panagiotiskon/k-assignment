package backend.kassignment.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductNotFoundException extends ResponseStatusException {

    private static final String reason ="Product not found";
    private ProductNotFoundException(HttpStatus status, String reason) {super(status, reason);}
    public ProductNotFoundException(){this(HttpStatus.NOT_FOUND, reason);}
}
