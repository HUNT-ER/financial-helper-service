package com.boldyrev.financialhelper.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SpringSecurityConfig {

    private static final String[] SWAGGER_URL = {
        "/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/swagger-ui.html"};

    @Bean
    @ConditionalOnProperty(value = "app.dev-profile", havingValue = "true")
    public SecurityWebFilterChain securityFilterChainLocal(ServerHttpSecurity httpSecurity) {
        return httpSecurity.cors(withDefaults())
            .csrf(withDefaults())
            .cors(withDefaults())
            .oauth2ResourceServer(oauth -> oauth.jwt(withDefaults()))
            .authorizeExchange(r -> r.anyExchange().permitAll())
            .build();
    }

    @Bean
    @ConditionalOnExpression("!'true'.equalsIgnoreCase('${app.dev-profile}')")
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity.cors(withDefaults())
            .csrf(withDefaults())
            .cors(withDefaults())
            .oauth2ResourceServer(oauth -> oauth.jwt(withDefaults()))
            .authorizeExchange(
                r -> r.pathMatchers(SWAGGER_URL).permitAll()
                    .anyExchange().authenticated())
            .build();
    }
}
