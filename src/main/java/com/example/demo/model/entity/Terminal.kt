package com.example.demo.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@Table(name = "terminal")
@Data
@NoArgsConstructor
@AllArgsConstructor
class Terminal {
    @Id
    private var tid: Long? = null

    private var tname: String? = null
}