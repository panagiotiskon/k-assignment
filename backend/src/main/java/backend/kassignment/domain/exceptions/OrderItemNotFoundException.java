package backend.kassignment.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class OrderItemNotFoundException extends ResponseStatusException {
    private static final String reason = "Order item not found";
    private OrderItemNotFoundException(HttpStatus status, String reason) {super(status, reason);}
    public OrderItemNotFoundException() {this(HttpStatus.NOT_FOUND, reason);}
}
