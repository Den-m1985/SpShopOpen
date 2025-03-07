package ru.spshop.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import ru.spshop.services.JpaUserDetailsService;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.HstsConfig;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    //private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    //@Autowired
   // private RsaKeyConfigProperties rsaKeyConfigProperties;
    @Autowired
    private JpaUserDetailsService userDetailsService;


    @Bean
    public AuthenticationManager authManager() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        return http
                .csrf(csrf -> {
                    csrf.disable();
                })
                .cors(cors -> cors.disable())
                .headers(header -> header.httpStrictTransportSecurity(HstsConfig::disable))
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
//                .authorizeHttpRequests(auth -> {
//                    //auth.requestMatchers("/error/**").permitAll();
//                    auth.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
//                    auth.requestMatchers("/api/**").permitAll();
//                    auth.anyRequest().authenticated();
//                })
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //.oauth2ResourceServer((oauth2) -> oauth2.jwt((jwt) -> jwt.decoder(jwtDecoder())))
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }


//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return NimbusJwtDecoder.withPublicKey(rsaKeyConfigProperties.publicKey()).build();
//    }


//    @Bean
//    JwtEncoder jwtEncoder() {
//        JWK jwk = new RSAKey.Builder(rsaKeyConfigProperties.publicKey()).privateKey(rsaKeyConfigProperties.privateKey()).build();
//        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
//        return new NimbusJwtEncoder(jwks);
//    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/api/**", configuration); // Укажите путь, к которому применяется конфигурация
//        return source;
//    }

}
