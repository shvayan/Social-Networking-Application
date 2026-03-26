package org.example.helloapp.util;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final Map<String, Bucket> bucketCache = new ConcurrentHashMap<>();


    private Bucket newBucket() {
        Bandwidth limit = Bandwidth.simple(50, Duration.ofMinutes(2)); //50 requests per 2 minute
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private Bucket resolveBucket(String ip) {


        return bucketCache.computeIfAbsent(ip, k -> newBucket());
        //If the IP already has a bucket, return it.
        // If the IP doesn’t have a bucket, create one.
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.startsWith("/api/v1/auth") ||
                path.equals("/") ||
                path.equals("/home") ||
                path.equals("/post") ||
                path.equals("/profile") ||
                path.equals("/chat") ||
                path.equals("/login") ||
                path.equals("/verify") ||
                path.equals("/postView") ||
                path.equals("/guestProfile") ||
                path.startsWith("/api/v1/post/getPosts") ||
                path.startsWith("/uploads")) {

            filterChain.doFilter(request, response);
            return;
        }
        String ip = request.getRemoteAddr();
        Bucket bucket = resolveBucket(ip);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            sendErrorResponse(response);
        }
    }

    private void sendErrorResponse(HttpServletResponse response)
            throws IOException {

        SecurityContextHolder.clearContext();
        response.setStatus(429);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("""
        {
          "success": false,
          "message": "%s"
        }
        """.formatted("Too many requests"));

        response.getWriter().flush();
    }
}