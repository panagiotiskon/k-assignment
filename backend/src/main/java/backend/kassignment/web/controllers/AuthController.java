package backend.kassignment.web.controllers;

import backend.kassignment.domain.User;
import backend.kassignment.security.JwtGenerator;
import backend.kassignment.service.JwtService;
import backend.kassignment.service.UserService;
import backend.kassignment.web.mappers.AuthResourceMapper;
import backend.kassignment.web.requests.LoginRequest;
import backend.kassignment.web.resources.AuthResource;
import backend.kassignment.web.resources.UserResource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final JwtService jwtService;
    private final JwtGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;
    private final AuthResourceMapper authResourceMapper;
    private final UserService userService;

    public AuthController(JwtService jwtService, JwtGenerator jwtGenerator, AuthenticationManager authenticationManager, AuthResourceMapper authResourceMapper, UserService userService) {
        this.jwtService = jwtService;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
        this.authResourceMapper = authResourceMapper;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResource> login(@RequestBody LoginRequest loginRequest,
                                              HttpServletResponse response) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        String token = authenticateUser(email, password);
        String type = "accessToken";
        Cookie jwtCookie = jwtService.createCookie(type, token);
        response.addCookie(jwtCookie);
        AuthResource authResource = authResourceMapper.toAuthResource(token, email);
        return new ResponseEntity<>(authResource, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        response.addCookie(jwtService.returnEmptyCookie());
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }


    @GetMapping("/current-user")
    public ResponseEntity<UserResource> getCurrentUser(@CookieValue(value = "accessToken", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            String email = jwtGenerator.getUsernameFromJWT(token);
            User user = userService.findUserByEmail(email);
            UserResource userResource = new UserResource(user);
            return new ResponseEntity<>(userResource, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }


    private String authenticateUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtGenerator.generateToken(authentication);
    }


}
