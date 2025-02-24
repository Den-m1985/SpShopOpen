package ru.spshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    //final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    SecurityWebFilterChain actuatorHttpSecurity(ServerHttpSecurity http) {
        http
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/actuator/**"))
                .authorizeExchange((exchanges) -> exchanges
                        .anyExchange().permitAll()
                )
                .cors(ServerHttpSecurity.CorsSpec::disable);
        return http.build();
    }

//    @Bean
//    SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
//        log.info("SecurityConfig/apiHttpSecurity:");
//        http
//                //.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                //.addFilterBefore(jwtAuthenticationFilter, SecurityWebFiltersOrder.OAUTH2_AUTHORIZATION_CODE) // ???????
//                //.securityMatcher(new PathPatternParserServerWebExchangeMatcher("/auth/**"))
//                .authorizeExchange((exchanges) -> exchanges
//                        .pathMatchers("/auth/**").permitAll()
//                        .anyExchange().authenticated()
//                )
//                //.oauth2ResourceServer(OAuth2ResourceServerSpec::jwt)
//                .cors(ServerHttpSecurity.CorsSpec::disable);
//        return http.build();
//    }

}
