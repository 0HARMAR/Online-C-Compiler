# Online C Compiler - Spring Boot + Vue

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Vue](https://img.shields.io/badge/Vue-3.3.4-brightgreen)](https://vuejs.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)](https://spring.io/projects/spring-boot)

🌐 基于Spring Boot和Vue构建的在线C语言编译执行平台，支持实时代码编辑、编译执行和结果反馈。

![Screenshot](./screenshot.png) <!-- 如有截图可替换或删除 -->

## ✨ 功能特性

- **实时编译** - 即时编译执行C语言代码
- **代码高亮** - 支持语法高亮和智能缩进
- **错误提示** - 显示编译错误和运行时异常
- **历史记录** - 自动保存用户代码执行历史
- **多设备支持** - 响应式布局适配不同屏幕
- **安全沙箱** - 使用Docker隔离代码执行环境

## 🛠️ 技术栈

**后端**
- Spring Boot 3.1.5
- Spring Security
- Jython (Python编译器集成)
- H2 Database / MySQL
- Docker API

**前端**
- Vue 3 + TypeScript
- Element Plus UI
- CodeMirror 代码编辑器
- Axios HTTP Client

**部署**
- Docker
- Nginx
- Jenkins (CI/CD)

## 🚀 快速开始

### 环境要求
- JDK 17+
- Node.js 16+
- Maven 3.9+
- Docker 20.10+

### 安装步骤

1. 克隆仓库
```bash
git clone https://github.com/yourusername/online-c-compiler.git
