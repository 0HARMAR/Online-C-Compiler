plugins {
    java
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "1.9.22"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(kotlin("stdlib-jdk8"))
    implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")
    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")

    implementation ("io.jsonwebtoken:jjwt-api:0.11.5")   // 编译时API
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")     // 运行时实现
    runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5")  // Jackson序列化

    implementation ("com.aliyun.oss:aliyun-sdk-oss:3.17.0")



}

tasks.withType<Test> {
    useJUnitPlatform()
}
