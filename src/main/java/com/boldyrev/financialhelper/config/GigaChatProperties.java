package com.boldyrev.financialhelper.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Properties for GigaChat API.
 *
 * @author Alexandr Boldyrev
 */
@Getter
@Component
public class GigaChatProperties {

    @Value("${app.gigachat.generation-model}")
    private String generationModel;

    @Value("${app.gigachat.generation-api-url}")
    private String generationApiUrl;

    @Value("${app.gigachat.auth-url}")
    private String authUrl;

    @Value("${app.gigachat.auth-token}")
    private String authToken;

    @Value("${app.gigachat.generation-categories-prompt}")
    private String generationCategoriesPrompt;

    @Value("${app.gigachat.api-scope}")
    private String apiScope;
}
