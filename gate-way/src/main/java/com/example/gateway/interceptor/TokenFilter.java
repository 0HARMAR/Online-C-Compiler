package com.example.gateway.interceptor;

    import com.example.gateway.utils.JwtUtils;
    import io.jsonwebtoken.Claims;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Component;
    import org.springframework.web.server.ServerWebExchange;
    import org.springframework.web.server.WebFilter;
    import org.springframework.web.server.WebFilterChain;
    import reactor.core.publisher.Mono;

    @Component
    public class TokenFilter implements WebFilter {
        @Override
        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
            String requestURI = exchange.getRequest().getURI().getPath();
            if (requestURI.contains("login")) {
                return chain.filter(exchange);
            }

            String token = exchange.getRequest().getHeaders().getFirst("token");
            if (token == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            try {
                Claims claims = JwtUtils.parseJwt(token);
                exchange.getAttributes().put("jwtClaims", claims);
            } catch (Exception e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        }
    }