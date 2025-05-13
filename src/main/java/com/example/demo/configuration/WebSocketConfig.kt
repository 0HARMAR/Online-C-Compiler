package com.example.demo.configuration

import com.example.demo.controller.TerminalWebSocketHandler
import com.example.demo.service.DockerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
open class WebSocketConfig(
    // Tell Spring to lazily initialize this dependency
    @Lazy private val terminalWebSocketHandler: TerminalWebSocketHandler
) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(terminalWebSocketHandler, "/terminal")
            .setAllowedOrigins("*")
    }

    @Bean
    open fun createTerminalWebSocketHandler(dockerService: DockerService): TerminalWebSocketHandler {
        return TerminalWebSocketHandler(dockerService)
    }
}