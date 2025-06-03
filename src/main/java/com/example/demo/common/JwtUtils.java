package com.example.demo.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtUtils {
    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static Integer id;
    public static String username;
    public static String password;

    public static String generateJwt(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id",id);
        claims.put("username",username);
        claims.put("password",password);
        return Jwts.builder().signWith(key,SignatureAlgorithm.HS256)
                .addClaims(claims)
                .compact();
    }

    public static Claims parseJwt(String jwt){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static String getUsername(Claims claims){
        return claims.get("username", String.class);
    }
}
