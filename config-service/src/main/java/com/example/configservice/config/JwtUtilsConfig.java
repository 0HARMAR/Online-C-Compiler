package com.example.configservice.config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

public class JwtUtilsConfig {
    public static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public static SecretKey generatePublicKey() {
        return key;
    }
}
