package backend.kassignment.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    private final Map<String, Bucket> ipBucketMap = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String clientIp = request.getRemoteAddr();
        Bucket bucket = ipBucketMap.computeIfAbsent(clientIp, this::createNewBucket);
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(429);
            response.getWriter().write("Rate limit exceeded. Try again later.");
        }
    }

    private Bucket createNewBucket(String ip) {
        Bandwidth limit = Bandwidth.classic(MAX_REQUESTS_PER_MINUTE,
                Refill.intervally(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1)));
        return Bucket4j.builder()
                .addLimit(limit)
                .build();
    }
}
