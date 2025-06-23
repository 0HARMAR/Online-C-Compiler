package com.example.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.awt.image.DataBuffer;

public class AddJwtTokenFilter extends AbstractGatewayFilterFactory<AddJwtTokenFilter.Config> {

    @Override
    public GatewayFilter apply(AddJwtTokenFilter.Config config) {
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (isLoginEndpoint(exchange.getRequest().getURI().getPath())) {
                    ServerHttpResponse response = exchange.getResponse();

                    // 检查响应是否成功
                    if(response.getStatusCode() == HttpStatus.OK){
                        // 获取响应体
                        DataBuffer dataBuffer = response.bufferFactory().wrap(response.getBody() != null ?
                                response.getBody() : new byte[0]);
                    }
                }
            }));
        };
    }

    private boolean isLoginEndpoint(String path) {
        // Check if the path is for the login endpoint
        return path.contains("/login");
    }

    public static class Config {
    }
}
