package backend.kassignment.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderNotFoundException extends ResponseStatusException {
    private static final String reason = "Order not found";
    private OrderNotFoundException(HttpStatus status, String reason) {super(status, reason);}
    public OrderNotFoundException() {this(HttpStatus.NOT_FOUND, reason);}
}