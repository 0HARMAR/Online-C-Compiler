package com.example.demo.configuration

import com.example.demo.controller.TerminalWebSocketHandler
import com.example.demo.service.DockerService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
open class WebSocketConfig(
    private val terminalWebSocketHandler: TerminalWebSocketHandler
) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(terminalWebSocketHandler, "/terminal")
            .setAllowedOrigins("*")
    }

    @Bean
    open fun createTerminalWebSocketHandler(dockerService: DockerService): TerminalWebSocketHandler {
        // Spring 会自动将 DockerService 注入到参数中
        return TerminalWebSocketHandler(dockerService)
    }
}