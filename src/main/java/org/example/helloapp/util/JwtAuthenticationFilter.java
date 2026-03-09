package org.example.helloapp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.helloapp.exception.UnauthorisedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Value("${jwt.secret}")
    private  String SECRET;

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
                path.startsWith("/chatApp") ||
                path.startsWith("/uploads")) {

            filterChain.doFilter(request, response);
            return;
        }

        try {

            String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                sendErrorResponse(response, "Missing or Invalid Authorization Header");
                return;
            }

            String token = header.substring(7);

            SecretKey secretKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date expiration = claims.getExpiration();

            if (expiration.before(new Date())) {
                sendErrorResponse(response, "Token Expired");
                return;
            }

            Long userId = claims.get("userId", Long.class);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, List.of());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            System.out.println("Authentication error: " + e.getMessage());
            sendErrorResponse(response, "Invalid or Expired Token");
        }
    }

    private void sendErrorResponse(HttpServletResponse response, String message)
            throws IOException {

        SecurityContextHolder.clearContext();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write("""
        {
          "success": false,
          "message": "%s"
        }
        """.formatted(message));

        response.getWriter().flush();
    }
}