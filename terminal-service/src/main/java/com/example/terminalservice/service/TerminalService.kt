package com.example.demo.service

interface TerminalService {
    /**
     * create a terminal
     */
    fun createTerminal(name:String,token: String)

    /**
     * interact with a terminal
     */
}