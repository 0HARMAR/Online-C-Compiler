package com.example.demo.service

import java.io.File

interface DockerService {
    fun buildImage(dockerfilePath: File, tag: String): String
}