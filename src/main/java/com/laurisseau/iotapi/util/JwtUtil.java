package com.laurisseau.iotapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtUtil {
    private final String secret;
    @Autowired
    public JwtUtil(
            @Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Date issuedAt = new Date(currentTimeMillis);
            Date expiration = new Date(currentTimeMillis + 1000 * 60 * 60 * 10); // 10 hours

            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(issuedAt)
                    .setExpiration(expiration)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        } catch (Exception e) {
            // Handle exceptions appropriately, e.g., log and return an error response.
            e.printStackTrace();
            return "Something went Wrong making Token";
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String decodeEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }


}
