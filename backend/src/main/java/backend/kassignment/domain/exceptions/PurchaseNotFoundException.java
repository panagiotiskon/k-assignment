package backend.kassignment.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PurchaseNotFoundException extends ResponseStatusException {
    private static final String reason = "Purchase not found";
    private PurchaseNotFoundException(HttpStatus status, String reason) {super(status, reason);}
    public PurchaseNotFoundException() {this(HttpStatus.NOT_FOUND, reason);}
}