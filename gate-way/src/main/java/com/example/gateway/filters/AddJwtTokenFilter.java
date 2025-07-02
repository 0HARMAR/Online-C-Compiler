package com.example.gateway.filters;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;



    @Override
    public GatewayFilter apply(AddJwtTokenFilter.Config config) {
        return (exchange, chain) -> {

                                }
                }
        };
    }

    private boolean isLoginEndpoint(String path) {
        // Check if the path is for the login endpoint
        return path.contains("/login");
    }

    public static class Config {
    }
    }
