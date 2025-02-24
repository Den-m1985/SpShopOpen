package ru.spshop.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component
public class LoggingGlobalPreFilter implements GlobalFilter {
    /*
    https://for-each.dev/lessons/b/-spring-cloud-custom-gateway-filters
     */
    //final Logger log = LoggerFactory.getLogger(LoggingGlobalPreFilter.class);

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {
        //log.info("Global Pre Filter executed");
        return chain.filter(exchange);
    }

}
