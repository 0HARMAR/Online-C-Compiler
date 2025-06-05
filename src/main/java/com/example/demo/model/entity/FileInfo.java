package com.example.demo.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "file_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    @Id
    @Column(name = "file_id", length = 36)
    private String fileId;

    @Column(name = "task_id", length = 36)
    private String taskId;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    private FileType fileType;

    @Column(name = "file_name", insertable = false, updatable = false)
    private String fileName;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "file_url", nullable = false, length = 512)
    private String fileUrl;

    @Column(name = "hash_sha256", nullable = false, length = 64)
    private String hashSha256;

    @Enumerated(EnumType.STRING)
    @Column(name = "encoding")
    private Encoding encoding = Encoding.UTF_8;

    @Column(name = "has_bom")
    private boolean hasBom = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "owner")
    private String owner;

    public enum FileType {
        C_SOURCE, HEADER, STATIC_LIB, SHARED_LIB, OBJECT, MAKEFILE
    }

    public enum Encoding {
        UTF_8("utf8"),
        GBK("gbk"),
        ASCII("ascii");

        private final String value;

        Encoding(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
} 