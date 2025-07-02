package com.example.gateway.filters;

import com.example.gateway.utils.JwtUtils;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static org.springframework.boot.web.server.Ssl.ClientAuth.map;

@Configuration
public class LoggingConfig implements Ordered {

//    @Bean
    public WebFilter responseLoggingFilter() {
        return (exchange, chain) -> {
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(exchange.getResponse()) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                   return super.writeWith(DataBufferUtils.join(body)
                           .doOnNext(dataBuffer -> {
                               try{
                                   //获取响应内容
                                   String responseBody = dataBuffer.toString(StandardCharsets.UTF_8);
                                   System.out.println("Response Body: " + responseBody);
                               }
                               finally {

                               }
                           })
                    );
                }
            };
            // 替换原始响应并继续执行
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        };
    }

    @Bean
    @Order(-20)
    public WebFilter addJwtTokenFilterTest() {
        return (exchange, chain) -> {
            ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(exchange.getResponse()) {
                @Override
                public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                    getHeaders(). add("token", JwtUtils.generateJwt());
                    return super.writeWith(DataBufferUtils.join(body)
                            .doOnNext(dataBuffer -> {
                                try{
                                    //获取响应内容
                                    String responseBody = dataBuffer.toString(StandardCharsets.UTF_8);
                                    System.out.println("Response Body Test: " + responseBody);
                                }
                                finally {

                                }
                            })
                    );
                }
            };
            return chain.filter(exchange.mutate().response(decoratedResponse).build());
        };
    }

    @Override
    public int getOrder() {
        return -10;
    }
}
