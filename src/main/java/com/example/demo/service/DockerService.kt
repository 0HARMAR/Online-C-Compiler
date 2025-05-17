package com.example.demo.service

import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.model.BuildResponseItem
import com.github.dockerjava.api.model.Frame
import java.io.File

interface DockerService {
    /**
     * Builds a Docker image from a Dockerfile
     * @param dockerfilePath Path to the Dockerfile
     * @param tag Tag to apply to the built image
     * @return The ID of the built image
     */
    fun buildImage(dockerfilePath: File, tag: String): String

    /**
     * Creates a new container from an image
     * @param image The image to create the container from
     * @return The ID of the created container
     */
    fun createContainer(image: String): String

    /**
     * Starts a container
     * @param containerId The ID of the container to start
     */
    fun startContainer(containerId: String)

    /**
     * Executes a command in a running container and returns the output
     * @param containerId The ID of the container
     * @param command The command to execute
     * @return The command output
     */
    fun execCommand(containerId: String, command: String): String

    /**
     * stop a container by containerId
     * @param containerId The ID of the container
     */
    fun stopContainer(containerId: String)

    /**
     * Check if there is an ubuntu image
     * @return The Boolean result
     */
    fun haveUbuntuImage(): Boolean

    /**
     * check whether you have an ubuntu container
     * if not create a container,returned the container id
     * if you have returned the first container id
     * @return The container id
     */
    fun findOrCreateUbuntuContainer(): String

    /**
     * create a terminal session by containerId
     * @param containerId
     * @return the terminalExecId
     */
    fun createTerminalSession(containerId: String): String
}