package ru.spshop.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * It passes incoming requests through itself, checks the user for his status - authorized or not.
 */
@Component
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {
    final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtUtil jwtUtil;
    private final GetUrlPathFromRequest getUrlPathFromRequest;


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String lastPath = getUrlPathFromRequest.getLastUrlPath(request);
            log.info("lastPath: {}", lastPath);
            if (isPathContainsTest(lastPath, "test")) {
                return chain.filter(exchange);
            }
            if (!isAuthMissing(request)) {
                final String jwtToken = getAuthHeader(request);
                if (jwtUtil.isInvalid(jwtToken)) {
                    log.info("Authorization token is invalid");
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                }
                return chain.filter(exchange);
            }
            return this.onError(exchange, "Authorization header miss token", HttpStatus.UNAUTHORIZED);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        if (!request.getHeaders().containsKey("Authorization")) {
            return true;
        }
        return !request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ");
    }

    public boolean isPathContainsTest(String strRequest, String staticPath) {
        return strRequest.equals(staticPath);
    }

    public JwtFilter(JwtUtil jwtUtil, GetUrlPathFromRequest getUrlPathFromRequest) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.getUrlPathFromRequest = getUrlPathFromRequest;
    }

    public static class Config {
    }

}
