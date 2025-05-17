package com.example.demo.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "terminal")
data class Terminal(
    @Id
    var tid: Long? = null,
    var tname: String? = null
)