package com.yourcompany.authservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class JwtUtil {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(String email, Set<String> roles) {
        Key key = getSignInKey();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("sub", email);
        claims.put("iat", (System.currentTimeMillis() / 1000));
        claims.put("exp", ((System.currentTimeMillis() + jwtExpiration) / 1000));
        return Jwts.builder()
                .claims(claims)
                .signWith(key)
                .compact();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).get("sub", String.class);
    }

    private Claims extractAllClaims(String token) {
        Key key = getSignInKey();
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}