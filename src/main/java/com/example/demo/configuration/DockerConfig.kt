package com.example.demo.configuration

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.core.DockerClientConfig
import com.github.dockerjava.core.DefaultDockerClientConfig
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient
import com.github.dockerjava.core.DockerClientImpl
import com.github.dockerjava.transport.DockerHttpClient
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "docker.images")
open class DockerConfig {
    lateinit var ubuntu: String
    @Bean
    open fun dockerClient(): DockerClient {
        val config: DockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder().build()
        val httpClient: DockerHttpClient = ApacheDockerHttpClient.Builder()
            .dockerHost(config.dockerHost)
            .sslConfig(config.sslConfig)
            .build()
        return DockerClientImpl.getInstance(config, httpClient)
    }
}