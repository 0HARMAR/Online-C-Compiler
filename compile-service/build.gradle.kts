plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    maven {
        url = uri("https://maven.aliyun.com/repository/public")
    }

    maven {
        url = uri("https://mirrors.ustc.edu.cn/nexus/content/repositories/central/")
    }

    mavenCentral()
}

extra["springCloudVersion"] = "2024.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(kotlin("stdlib-jdk8"))

    implementation(project(":upload-service"))
    implementation(project(":common"))

    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")   // 编译时API
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")     // 运行时实现
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5")  // Jackson序列化

    // MyBatis Spring Boot Starter（核心依赖）
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")

    // 数据库驱动（以 MySQL 为例）
    runtimeOnly("mysql:mysql-connector-java:8.0.33")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
