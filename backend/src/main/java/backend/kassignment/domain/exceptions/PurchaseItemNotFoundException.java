package backend.kassignment.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PurchaseItemNotFoundException extends ResponseStatusException {
    private static final String reason = "Purchase item not found";
    private PurchaseItemNotFoundException(HttpStatus status, String reason) {super(status, reason);}
    public PurchaseItemNotFoundException() {this(HttpStatus.NOT_FOUND, reason);}
}
