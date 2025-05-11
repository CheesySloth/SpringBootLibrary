package com.library.core.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

public class JwtService {
    private static final String SECRET_KEY = "74ee2c971972ffe8f680d4ed3129b6810415d3afd0645f2f9917a7132a7df6c5ffdb591e84a9c4021eb97337749a266044e71369f60a83988dcecb395340fe64f1b7724a8f97018c930548fdd436187ad2bc671c7962a1de81fc7f222f5729ccd3e699271d46fde3e40af4249e73df49389aaa5e02570723a885d91d1a53b1e5de13c5dcc0cb3bfb85c497c3723ce7db7198c6593dcffd57e9286d266457efef463857a2ba2c35b900649106b0e995742c6e109faa3e52f862230b9800ae98fae0a54667b96bdb5b1aa0ceb8a9d27781a5b96138173a1edde94a0e4f3078401cd5f183dbf01a82f12669027d87e823c3725748497e896542ef80e853185b536e";

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return resolver.apply(claims);
    }

    public boolean isTokenValid(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username)) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

}
