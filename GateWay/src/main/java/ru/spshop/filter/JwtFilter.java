package ru.spshop.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * It passes incoming requests through itself, checks the user for his status - authorized or not.
 */
@Component
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {
    final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    @Autowired
    private JwtUtil jwtUtil;
//    @Autowired
//    private WebClient webClient;
    @Autowired
    private GetUrlPathFromRequest getUrlPathFromRequest;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String lastPath = getUrlPathFromRequest.getLastUrlPath(request);
            log.info("lastPath: " + lastPath);
            // look for "/test"
            if (isPathContainsTest(lastPath, "test")) {
                return chain.filter(exchange);
            }
            if (!isAuthMissing(request)) {
                final String jwtToken = getAuthHeader(request);
                //log.info("JwtFilter/jwtToken: " + jwtToken);
                if (jwtUtil.isInvalid(jwtToken)) {
                    log.info("Authorization token is invalid");
                    return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
                }
                return chain.filter(exchange);
            }
            log.info("JwtAuth/apply/no_token");
            return this.onError(exchange, "Authorization header miss token", HttpStatus.UNAUTHORIZED);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    /**
     * Returns the token string from the request header.
     *
     * @param request
     * @return
     */
    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0).substring(7);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
//        for (Map.Entry<String, List<String>> b : request.getHeaders().entrySet()) {
//            log.info("key: " + b.getKey());
//            log.info("value: " + b.getValue().toString());
//        }
        if (!request.getHeaders().containsKey("Authorization")) {
            return true;
        }
        if (!request.getHeaders().getOrEmpty("Authorization").get(0).startsWith("Bearer ")) {
            return true;
        }
        return false;
    }

    public boolean isPathContainsTest(String strRequest, String staticPath) {
        return strRequest.equals(staticPath);
    }

    public JwtFilter() {
        super(Config.class);
    }

    public static class Config {
    }

    // it doesn't work
//    public Mono<Void> requestToAuth(ServerWebExchange exchange, GatewayFilterChain chain){
//        log.info("response: ");
//        Mono<String> response = webClient.get()
//                .uri("http://localhost:8000/auth/tests/test")
//                .retrieve()
//                .bodyToMono(String.class);
//        return response.flatMap(result -> {
//            // Process the result or forward it to the downstream service
//            // ...
//            log.info("result: " + result);
//            return chain.filter(exchange);
//        });
//    }

}
