package com.example.demo.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "compile_task")
data class CompileTask(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", length = 36)
    var taskId: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: Status? = null,

    @Column(name = "source_path", length = 512)
    var sourcePath: String? = null,

    @Column(name = "output_path", length = 512)
    var outputPath: String? = null,

    @Column(name = "user_id")
    var userId: Int? = null,

    @Column(name = "created_at", columnDefinition = "DATETIME(3)")
    var createdAt: LocalDateTime? = null,

    @Column(name = "updated_at", columnDefinition = "DATETIME(3)")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "compiler_version", length = 20)
    var compilerVersion: String? = null,

    @Column(name = "execution_time", unique = true)
    var executionTime: Int? = null,

    @Lob
    @Column(name = "error_log", columnDefinition = "TEXT")
    var errorLog: String? = null
) {
    enum class Status {
        pending, compiling, success, failed
    }
}