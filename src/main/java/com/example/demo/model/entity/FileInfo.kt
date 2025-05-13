package com.example.demo.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "file_info")
data class FileInfo(
    @Id
    @Column(name = "file_id", length = 36)
    var fileId: String? = null,  // 主键推荐用 val

    @Column(name = "task_id", length = 36)
    var taskId: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    var fileType: FileType? = null,

    @Column(name = "file_path", nullable = false, length = 512)
    var filePath: String? = null,

    @Column(name = "file_name", insertable = false, updatable = false)
    var fileName: String? = null,

    @Column(name = "file_size", nullable = false)
    var fileSize: Long? = null,

    @Column(name = "hash_sha256", nullable = false, length = 64)
    var hashSha256: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "encoding")
    var encoding: Encoding = Encoding.UTF_8,  // 直接使用枚举类型

    @Column(name = "has_bom")
    var hasBom: Boolean = false,

    @Column(name = "created_at")
    var createdAt: LocalDateTime? = null,

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null,

    @Column(name = "owner")
    var owner: String? = null
) {
    enum class FileType {
        C_SOURCE, HEADER, STATIC_LIB, SHARED_LIB, OBJECT, MAKEFILE
    }

    enum class Encoding(val value: String) {
        UTF_8("utf8"), GBK("gbk"), ASCII("ascii")
    }
}