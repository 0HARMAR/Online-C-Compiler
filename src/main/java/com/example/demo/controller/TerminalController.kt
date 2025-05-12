package com.example.demo.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TerminalController {
    @PostMapping("/terminals")
    fun handleCreateTerminal(@RequestBody body: String) {

    }
}