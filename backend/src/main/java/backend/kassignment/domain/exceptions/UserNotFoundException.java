package backend.kassignment.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFoundException  extends ResponseStatusException {

    private static final String reason ="User not found";
    private UserNotFoundException(HttpStatus status, String reason) {super(status, reason);}
    public UserNotFoundException(){this(HttpStatus.NOT_FOUND, reason);}
}

