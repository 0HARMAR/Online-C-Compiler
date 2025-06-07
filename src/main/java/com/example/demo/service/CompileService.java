package com.example.demo.service;

import com.example.demo.model.entity.CompileConfig;
import io.jsonwebtoken.Claims;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service interface for handling compilation-related operations.
 */
public interface CompileService {

    /**
     * Compiles the provided source signal file code based on the given configuration.
     *
     * @param option The compilation configuration, including compiler options and settings.
     * @param token  The JWT token used for authentication and authorization.
     * @param fileId The unique identifier of the file to be compiled.
     * @return the compile output download URL.
     */
    String compile(CompileConfig option, String token, String fileId);

}