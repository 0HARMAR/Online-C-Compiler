package com.example.gateway.filters;

import com.example.gateway.utils.JwtUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

//@Component
public class AddJwtTokenFilter extends AbstractGatewayFilterFactory<AddJwtTokenFilter.Config> implements Ordered {
    private final ObjectMapper objectMapper;

    public AddJwtTokenFilter(ObjectMapper objectMapper) {
        super(Config.class);
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(AddJwtTokenFilter.Config config) {
        return (exchange, chain) -> {
            if (!isLoginEndpoint(exchange.getRequest().getURI().getPath())) {
                // 如果不是登录端点，直接放行
                return chain.filter(exchange);
            }

            // 使用装饰器拦截响应体写入
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

    private byte[] mergeDataBuffers(List<DataBuffer> dataBuffers) {
        int totalSize = dataBuffers.stream().mapToInt(DataBuffer::readableByteCount).sum();
        byte[] result = new byte[totalSize];
        int offset = 0;
        for (DataBuffer dataBuffer : dataBuffers) {
            int readableBytes = dataBuffer.readableByteCount();
            dataBuffer.read(result, offset, readableBytes);
            offset += readableBytes;
            DataBufferUtils.release(dataBuffer); // 释放DataBuffer
        }
        return result;
    }


    private boolean isLoginEndpoint(String path) {
        // Check if the path is for the login endpoint
        return path.contains("/login");
    }

    public static class Config {
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }
}

