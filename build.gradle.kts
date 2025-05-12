plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("jvm")
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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
	runtimeOnly("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// MyBatis Spring Boot Starter（核心依赖）
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")

    // 数据库驱动（以 MySQL 为例）
    runtimeOnly("mysql:mysql-connector-java:8.0.33")

	implementation ("io.jsonwebtoken:jjwt-api:0.11.5")   // 编译时API
	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.11.5")     // 运行时实现
	runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.11.5")  // Jackson序列化

	implementation ("com.aliyun.oss:aliyun-sdk-oss:3.17.0")
	implementation(kotlin("stdlib-jdk8"))

	implementation("com.github.docker-java:docker-java-core:3.3.4")
	implementation("com.github.docker-java:docker-java-transport-httpclient5:3.3.4")
	implementation("org.springframework.boot:spring-boot-starter-websocket")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
