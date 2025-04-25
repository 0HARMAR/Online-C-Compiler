package com.example.demo.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompileConfig {
    private String compilerType;
    private String compilerVersion;
    private String compilerArgs;

}
