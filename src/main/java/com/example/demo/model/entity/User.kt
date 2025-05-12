package com.example.demo.model.entity

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

// Spring Boot 3.x 使用 jakarta
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    private var name: String? = null
    private var password: String? = null
    private var token: String? = null
}