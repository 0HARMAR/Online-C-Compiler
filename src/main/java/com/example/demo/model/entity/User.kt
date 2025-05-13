package com.example.demo.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(nullable = false)  // 如果数据库列名是 "id" 可省略 name 属性
    var id: Long? = null,

    @field:Column(nullable = false, length = 255)  // 根据实际数据库约束添加注解
    var name: String? = null,

    @field:Column(nullable = false, length = 255)
    var password: String? = null,

    @field:Column(length = 512)  // 根据业务需求调整长度
    var token: String? = null
)