package com.example.demo.controller

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TerminalController {
    @PostMapping("/terminals")
    fun handleCreateTerminal(@RequestBody body: String) {

    }

    @PostMapping("/terminals/{tid}")
    fun handleDeleteTerminal(@PathVariable tid: String, @RequestBody body: String) {}
}