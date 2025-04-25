package com.example.demo.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateJwt(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","harmar");
        return Jwts.builder().signWith(key,SignatureAlgorithm.HS256)
                .addClaims(claims)
                .compact();
    }

    public static Claims parseJwt(String jwt){
        return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
    }

    public static String getUsername(Claims claims){
        return claims.get("username", String.class);
    }
}
