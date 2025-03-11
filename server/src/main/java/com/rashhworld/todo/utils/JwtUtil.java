package com.rashhworld.todo.utils;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
    private final SecretKey key = Jwts.SIG.HS256.key().build();

    public String generateToken(String username) {
        return Jwts.builder().subject(username).signWith(key).compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
}
