package com.example.demo.model.entity;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "file_info")
public class FileInfo {
    public enum FileType {
        C_SOURCE, HEADER, STATIC_LIB, SHARED_LIB, OBJECT, MAKEFILE
    }

    public enum Encoding {
        UTF_8("utf8"), GBK("gbk"), ASCII("ascii");

        private final String encoding;
        Encoding(String encoding) {
            this.encoding = encoding;
        }

        public String getEncoding() {
            return encoding;
        }
    }

    @Id
    @Column(name = "file_id", length = 36)
    @NonNull
    private String fileId;

    @Column(name = "task_id", length = 36)
    private String taskId;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type", nullable = false)
    @NonNull
    private FileType fileType;

    @Column(name = "file_path", nullable = false, length = 512)
    @NonNull
    private String filePath;

    @Column(name = "file_name", insertable = false, updatable = false)
    private String fileName;

    @Column(name = "file_size", nullable = false)
    @NonNull
    private Long fileSize;

    @Column(name = "hash_sha256", nullable = false, length = 64)
    @NonNull
    private String hashSha256;

    @Column(name = "encoding")
    private String encoding = Encoding.UTF_8.encoding;

    @Column(name = "has_bom")
    private Boolean hasBom = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "owner")
    private String owner;
}