package backend.kassignment.service;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    /**
     * This method returns a cookie containing a JWT, configured for secure HTTP-only access.
     *
     * @param type The name of the cookie indicating the type of the cookie.
     * @param token The JWT token to be stored within the cookie.
     * @return A {@link Cookie} containing the JWT
     */

    public Cookie createCookie(String type, String token){
        Cookie cookie = new Cookie(type, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        return cookie;
    }

    /**
     * This method returns an empty Cookie, effectively clearing it
     * by setting the value to null and maxAge to 0.
     * @return A {@link Cookie}
     */

    public Cookie returnEmptyCookie() {
        Cookie jwtCookie = new Cookie("accessToken", null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        return jwtCookie;
    }

}
