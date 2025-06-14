package org.example.compile.service;

import com.example.common.entity.CompileConfig;
import com.example.common.result.CompileResult;

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
    CompileResult compile(CompileConfig option, String token, String fileId);

}