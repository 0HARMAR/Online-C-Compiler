// src/main/java/com/example/demo/model/entity/Terminal.kt
package com.example.demo.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "terminal")
data class Terminal(
    @Id
    var tid: Long? = null,

    var tname: String? = null,

    @OneToOne
    @JoinColumn(name = "tid", referencedColumnName = "id")
    @MapsId
    var user: User? = null
)