package com.example.demo.model.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDateTime

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compile_task")
class CompileTask {
    enum class Status {
        pending, compiling, success, failed
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", length = 36)
    private var taskId: String? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private var status: Status? = null

    @Column(name = "source_path", length = 512)
    private var sourcePath: String? = null

    @Column(name = "output_path", length = 512)
    private var outputPath: String? = null

    @Column(name = "user_id")
    private var userId: Int? = null

    @Column(name = "created_at", columnDefinition = "DATETIME(3)")
    private var createdAt: LocalDateTime? = null

    @Column(name = "updated_at", columnDefinition = "DATETIME(3)")
    private var updatedAt: LocalDateTime? = null

    @Column(name = "compiler_version", length = 20)
    private var compilerVersion: String? = null

    @Column(name = "execution_time", unique = true)
    private var executionTime: Int? = null

    @Lob
    @Column(name = "error_log", columnDefinition = "TEXT")
    private var errorLog: String? = null
}
