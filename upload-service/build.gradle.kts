plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()

    flatDir { dirs("common") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.4")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(project(":common"))

    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")   // 编译时API
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")     // 运行时实现
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5")  // Jackson序列化

    implementation("org.jetbrains:annotations:24.1.0")

    implementation ("com.aliyun.oss:aliyun-sdk-oss:3.17.0")

    // MyBatis Spring Boot Starter（核心依赖）
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")

    // 数据库驱动（以 MySQL 为例）
    runtimeOnly("mysql:mysql-connector-java:8.0.33")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
