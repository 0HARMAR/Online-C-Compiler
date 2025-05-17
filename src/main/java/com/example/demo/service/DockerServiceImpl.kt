package com.example.demo.service

import com.example.demo.configuration.DockerConfig
import com.github.dockerjava.api.DockerClient
import com.github.dockerjava.api.async.ResultCallback
import com.github.dockerjava.api.exception.NotFoundException
import com.github.dockerjava.api.model.BuildResponseItem
import com.github.dockerjava.api.model.Frame
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.io.File

@Service
class DockerServiceImpl(
    private val dockerClient: DockerClient,
    private val dockerConfig: DockerConfig
) : DockerService {

    // 创建镜像（从Dockerfile）
    override fun buildImage(dockerfilePath: File, tag: String): String {
        val cmd = dockerClient.buildImageCmd()
            .withDockerfile(dockerfilePath)
            .withTags(setOf(tag))

        val callback = object : ResultCallback.Adapter<BuildResponseItem>() {
            var imageId: String? = null

            override fun onNext(item: BuildResponseItem) {
                // 从构建输出中提取镜像 ID
                if (item.isBuildSuccessIndicated) {
                    imageId = item.imageId
                }
            }

            override fun onError(throwable: Throwable) {
                throw RuntimeException("构建失败", throwable)
            }
        }

        cmd.exec(callback).awaitCompletion()  // 等待构建完成
        return callback.imageId ?: throw RuntimeException("未找到镜像 ID")
    }

    // 创建容器
    override fun createContainer(image: String): String {
        val container = dockerClient.createContainerCmd(image)
            .withTty(true) // 启用TTY
            .exec()
        return container.id
    }

    // 启动容器
    override fun startContainer(containerId: String) {
        dockerClient.startContainerCmd(containerId).exec()
    }

    // 执行命令并获取输出
    override fun execCommand(containerId: String, command: String): String {
        val execId = dockerClient.execCreateCmd(containerId)
            .withCmd(command.split(" ").toString())
            .withAttachStdout(true)
            .withAttachStderr(true)
            .exec()
            .id

        val output = ByteArrayOutputStream()
        dockerClient.execStartCmd(execId).exec(object : ResultCallback.Adapter<Frame>() {
            override fun onNext(frame: Frame) {
                output.write(frame.payload)
            }
        }).awaitCompletion()

        return output.toString()
    }

    override fun stopContainer(containerId: String) {
        TODO("Not yet implemented")
    }

    override fun haveUbuntuImage(): Boolean {
        return try {
            val imageName: String = dockerConfig.ubuntu
            dockerClient.inspectImageCmd(imageName).exec()
            true
        } catch (e: NotFoundException) {
            false
        }
    }

    override fun findOrCreateUbuntuContainer(): String {
        // 检查现有容器 (包含已停止的容器)
        val containers = dockerClient.listContainersCmd()
            .withShowAll(true) // 包含所有状态的容器
            .withStatusFilter(listOf("created", "running", "exited")) // 按需过滤状态
            .exec()

        // 查找匹配镜像的容器
        val existingContainer = containers.firstOrNull { container ->
            container.image == dockerConfig.ubuntu // 假设配置中存储了镜像名
        }

        // 直接返回现有容器ID
        if (existingContainer != null) {
            return existingContainer.id
        }

        // 创建新容器（不自动启动）
        return createContainer(dockerConfig.ubuntu)
    }

    fun createTerminalSession(containerId: String): ExecSession {
        val execId = dockerClient.execCreateCmd(containerId)
            .withCmd("/bin/bash")
            .withAttachStdin(true)
            .withAttachStdout(true)
            .withAttachStderr(true)
            .withTty(true)
            .exec()
            .id

        return ExecSession(
            dockerClient = dockerClient,
            execId = execId
        )
    }

    fun interactWithSession(session: ExecSession) {
        // 启动会话并绑定输入输出流
        val execStartCmd = session.dockerClient.execStartCmd(session.execId).withTty(true)
        val result = execStartCmd.exec()

        // 输入输出流处理
        val stdin: OutputStream = result.standardInput
        val stdout: InputStream = result.standardOutput

        // 启动线程读取输出
        Thread {
            val buffer = ByteArray(1024)
            while (true) {
                val bytesRead = stdout.read(buffer)
                if (bytesRead > 0) {
                    val output = String(buffer, 0, bytesRead)
                    print(output) // 显示容器返回的输出
                }
            }
        }.start()

        // 主线程读取用户输入并发送到容器
        val scanner = Scanner(System.`in`)
        while (true) {
            print("> ") // 模拟终端提示符
            val command = scanner.nextLine()
            stdin.write("$command\n".toByteArray()) // 注意添加换行符（相当于按回车）
            stdin.flush()
        }
    }
}

data class ExecSession(
    val dockerClient: DockerClient,
    val execId: String
)