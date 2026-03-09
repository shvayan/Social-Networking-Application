package org.example.helloapp.util;



import io.jsonwebtoken.Jwts;

import lombok.RequiredArgsConstructor;
import org.example.helloapp.config.JwtConfig;

import org.springframework.stereotype.Component;


import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtHelper {


    private final JwtConfig jwtConfig;
    public String generatedTokenForUser(Long userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 24 * 1000 * 60 * 60); // 1 day
        return Jwts.builder().issuedAt(now).expiration(expiryDate).claim("userId", userId).claim("iss", "helloApp").claim("scope", "user").signWith(jwtConfig.secretKey()).compact();
    }
}
