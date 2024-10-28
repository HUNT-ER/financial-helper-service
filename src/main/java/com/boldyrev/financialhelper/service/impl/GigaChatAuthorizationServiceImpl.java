package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.financialhelper.config.GigaChatProperties;
import com.boldyrev.financialhelper.dto.AuthTokenDto;
import com.boldyrev.financialhelper.service.AuthorizationService;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
@Service
@RequiredArgsConstructor
public class GigaChatAuthorizationServiceImpl implements AuthorizationService {

    private static final String AUTH_TOKEN_KEY = "gigachat.auth";

    private final GigaChatProperties properties;

    private final WebClient webClient;

    private final ReactiveRedisTemplate<String, AuthTokenDto> redisTemplate;

    @Override
    public Mono<String> getToken() {
        return redisTemplate.opsForValue()
            .get(AUTH_TOKEN_KEY)
            .flatMap(token -> isExpired(token) ? Mono.empty() : Mono.just(token.getAccessToken()))
            .switchIfEmpty(
                Mono.defer(this::fetchToken)
                    .flatMap(token -> redisTemplate.opsForValue().set(AUTH_TOKEN_KEY, token)
                        .then(Mono.just(token)))
                    .map(AuthTokenDto::getAccessToken)
            );
    }

    private static boolean isExpired(AuthTokenDto token) {
        if (token != null && token.getExpiresAt() != null && token.getAccessToken() != null) {
            return new Date().after(new Date(token.getExpiresAt()));
        }
        return true;
    }

    private Mono<AuthTokenDto> fetchToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.put("scope", List.of(properties.getApiScope()));

        return webClient.post()
            .uri(properties.getAuthUrl())
            .header("rquid", UUID.randomUUID().toString())
            .header("Authorization", "Bearer %s".formatted(properties.getAuthToken()))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(AuthTokenDto.class);
    }
}
