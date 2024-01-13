package com.codespace.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class TokenManager {

    private static final String secretKey = "tastekineCodeSpace";

    private static final int validityTime = 5 * 60 * 1000;  // millisecond

    public String generateToken(String userName) {
        String jws = Jwts.builder()
                .setSubject(userName)
                .setIssuer("www.tastekine.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityTime))
                .signWith(SignatureAlgorithm.ES256, secretKey)
                .compact();
        return jws;
    }

    public boolean tokenValidate(String token) {
        if (getUserNameFromToken(token) != null && isExpired(token)) {
            return true;
        }
        return false;
    }

    public String getUserNameFromToken(String token) {
        // Claims are the tokens attributes that we set on the generateToken method.
        Claims claims = getClaims(token);
        // We just need userName so set it in subject -->
        return claims.getSubject();
    }

    public boolean isExpired(String token) {
        Claims claims = getClaims(token);
        return (claims.getExpiration().before(new Date(System.currentTimeMillis())));
    }

    private static Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
