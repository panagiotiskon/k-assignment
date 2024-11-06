package backend.kassignment.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingFilter implements Filter {

    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    private final Map<String, Bucket> ipBucketMap = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = httpRequest.getRemoteAddr();
        Bucket bucket = ipBucketMap.computeIfAbsent(clientIp, this::createNewBucket);

        if (bucket.tryConsume(1)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.setStatus(429); // Use 429 for "Too Many Requests"
            httpResponse.getWriter().write("Rate limit exceeded. Try again later.");
        }
    }

    private Bucket createNewBucket(String ip) {
        Bandwidth limit = Bandwidth.classic(MAX_REQUESTS_PER_MINUTE, Refill.intervally(MAX_REQUESTS_PER_MINUTE, Duration.ofMinutes(1)));
        return Bucket4j.builder().addLimit(limit).build();
    }

    @Override
    public void init(FilterConfig filterConfig) {
        // No initialization needed
    }

    @Override
    public void destroy() {
        // No resources to clean up
    }
}
