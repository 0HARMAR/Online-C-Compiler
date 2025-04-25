package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;
@Service
@Configuration
@Slf4j // 需要 lombok 依赖
public class CorsConfig implements WebMvcConfigurer {
    public CorsConfig() {
        log.info("CORS 配置初始化完成"); // 启动时查看控制台输出
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("应用 CORS 规则到所有路径"); // 关键日志点
        registry.addMapping("/hello")
                .allowedOrigins("http://127.0.0.1:5500")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}