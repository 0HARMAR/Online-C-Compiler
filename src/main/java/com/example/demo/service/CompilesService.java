package com.example.demo.service;

import com.example.demo.model.entity.CompileConfig;
import io.jsonwebtoken.Claims;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface CompilesService {
    /**
     * Compiles the provided source project code based on the given configuration.
     *
     * @param option The compilation configuration, including compiler options and settings.
     * @param token  The JWT token used for authentication and authorization.
     * @param fileId The unique identifier of the file to be compiled.
     * @return the compile output download URL.
     */
    String compiles(CompileConfig option, String token, String fileId);
}
