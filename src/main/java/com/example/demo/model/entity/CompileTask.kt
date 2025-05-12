package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compile_task")
public class CompileTask {
    public enum Status {
        pending, compiling, success, failed
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", length = 36)
    private String taskId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "source_path", length = 512)
    private String sourcePath;

    @Column(name = "output_path", length = 512)
    private String outputPath;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "created_at", columnDefinition = "DATETIME(3)")
    private LocalDateTime createdAt;

    @Column(name = "updated_at", columnDefinition = "DATETIME(3)")
    private LocalDateTime updatedAt;

    @Column(name = "compiler_version", length = 20)
    private String compilerVersion;

    @Column(name = "execution_time", unique = true)
    private Integer executionTime;

    @Lob
    @Column(name = "error_log", columnDefinition = "TEXT")
    private String errorLog;
}
