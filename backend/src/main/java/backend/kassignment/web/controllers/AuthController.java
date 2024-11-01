package backend.kassignment.web.controllers;

import backend.kassignment.security.JwtGenerator;
import backend.kassignment.service.AuthService;
import backend.kassignment.service.JwtService;
import backend.kassignment.web.mappers.AuthResourceMapper;
import backend.kassignment.web.requests.LoginRequest;
import backend.kassignment.web.resources.AuthResource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final JwtService jwtService;
    private final JwtGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;
    private final AuthResourceMapper authResourceMapper;

    public AuthController(JwtService jwtService, JwtGenerator jwtGenerator, AuthenticationManager authenticationManager, AuthResourceMapper authResourceMapper) {
        this.jwtService = jwtService;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
        this.authResourceMapper = authResourceMapper;
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

    private String authenticateUser(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtGenerator.generateToken(authentication);
    }


}
