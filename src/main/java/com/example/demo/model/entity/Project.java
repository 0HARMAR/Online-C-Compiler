package com.example.demo.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "projects")
@Data
public class Project {
    // 项目ID
    @Id
    @Column(name = "project_id", length = 36)
    private String projectId;

    // 项目名称
    @Column(name = "project_name", nullable = false, length = 100)
    private String projectName;

    // 项目所有人ID
    @Column(name = "owner_id", nullable = false, length = 50)
    private String ownerId;
}
