package com.boldyrev.financialhelper.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SpringSecurityConfig {

    private static final String[] SWAGGER_URL = {
        "/v3/api-docs/**",
        "/v3/api-docs",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/swagger-ui.html"};

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity.cors(withDefaults())
            .csrf(withDefaults())
            .oauth2ResourceServer(oauth -> oauth.jwt(withDefaults()))
            .authorizeExchange(r -> r.anyExchange().permitAll())
//            .authorizeExchange(
//                r -> r.pathMatchers(SWAGGER_URL).permitAll()
//                    .anyExchange().authenticated())
            .build();
    }
}
