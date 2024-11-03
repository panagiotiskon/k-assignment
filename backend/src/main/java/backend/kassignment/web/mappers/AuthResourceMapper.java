package backend.kassignment.web.mappers;

import backend.kassignment.domain.User;
import backend.kassignment.domain.repository.UserRepository;
import backend.kassignment.web.resources.AuthResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.UUID;

@Component
public class AuthResourceMapper {

    private final UserRepository userRepository;


    public AuthResourceMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResource toAuthResource(String token, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        AuthResource authResource = new AuthResource();
        authResource.setToken(token);
        authResource.setEmail(email);
        authResource.setRoles(user.getRoles());
        return authResource;
    }



}
