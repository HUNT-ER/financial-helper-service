package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.financialhelper.config.GigaChatProperties;
import com.boldyrev.financialhelper.dto.request.ChatMessageDto;
import com.boldyrev.financialhelper.dto.request.GigaChatGenerationRequest;
import com.boldyrev.financialhelper.dto.response.GigaChatGenerationResponse;
import com.boldyrev.financialhelper.dto.response.GigaChatGenerationResponse.GeneratedMessagesDto;
import com.boldyrev.financialhelper.exception.IncorrectCategorizationResponseException;
import com.boldyrev.financialhelper.model.ReceiptItem;
import com.boldyrev.financialhelper.model.TransactionCategory;
import com.boldyrev.financialhelper.service.AuthorizationService;
import com.boldyrev.financialhelper.service.ReceiptItemsCategorizationService;
import com.boldyrev.financialhelper.service.TransactionCategoriesService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Service for categorization items with GigaChat AI.
 *
 * @author Alexandr Boldyrev
 * @see <a href="https://giga.chat/">GigaChat</a>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class GigaChatReceiptItemCategorizationServiceImpl implements
    ReceiptItemsCategorizationService {

    private final WebClient webClient;

    private final GigaChatProperties gigaChatProperties;

    private final AuthorizationService authorizationService;

    private final TransactionCategoriesService categoriesService;


    @Override
    public Mono<Map<String, TransactionCategory>> categorizeItems(List<ReceiptItem> items) {
        List<String> itemNames = items.stream()
            .map(ReceiptItem::getName)
            .toList();

        StringJoiner templateItems = new StringJoiner("; ");
        HashMap<Integer, String> itemsMapping = new HashMap<>();
        for (int i = 0; i < itemNames.size(); i++) {
            String item = itemNames.get(i);

            templateItems.add("%d - %s".formatted(i, item));
            itemsMapping.put(i, itemNames.get(i));
        }

        log.debug("Starting categorization of items: size:{}, items:{}", itemNames.size(),
            templateItems);

        HashMap<String, TransactionCategory> categories = new HashMap<>();
        return categoriesService.getCategories()
            .doOnNext(category -> categories.put(category.getCategoryName(), category))
            .map(TransactionCategory::getCategoryName)
            .reduce(new StringJoiner(", "), StringJoiner::add)
            .flatMap(categoriesTemplate -> sendGenerationRequest(categoriesTemplate.toString(),
                templateItems.toString()))
            .flatMap(this::parseCategorizationResponse)
            .flatMap(categorizedItems -> mapItemsWithCategories(itemsMapping, categorizedItems, categories));
    }


    private List<ChatMessageDto> prepareChatMessages(String categories, String items) {
        ChatMessageDto promptMessage = ChatMessageDto.builder()
            .role("system")
            .content(gigaChatProperties.getGenerationCategoriesPrompt().formatted(categories))
            .build();

        ChatMessageDto itemsMessage = ChatMessageDto.builder()
            .role("user")
            .content(items)
            .build();

        return List.of(promptMessage, itemsMessage);
    }

    private Mono<GigaChatGenerationResponse> sendGenerationRequest(String categories,
        String items) {
        GigaChatGenerationRequest generationRequest = GigaChatGenerationRequest.builder()
            .model(gigaChatProperties.getGenerationModel())
            .chatMessages(prepareChatMessages(categories, items))
            .build();

        return authorizationService.getToken()
            .flatMap(token ->
                webClient.post()
                    .uri(gigaChatProperties.getGenerationApiUrl())
                    .header("x-request-id", UUID.randomUUID().toString())
                    .header("x_client_id", UUID.randomUUID().toString())
                    .header("x_client_id", UUID.randomUUID().toString())
                    .header("Authorization", "Bearer %s".formatted(token))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(generationRequest)
                    .retrieve()
                    .bodyToMono(GigaChatGenerationResponse.class)
            );
    }

    private Mono<Map<Integer, String>> parseCategorizationResponse(
        GigaChatGenerationResponse generationResponse) {
        if (generationResponse != null && !generationResponse.getChoices().isEmpty()) {
            GeneratedMessagesDto response = generationResponse.getChoices().getFirst();

            return Mono.just(Arrays.stream(
                    response.getMessage().getContent().split(", "))
                .filter(item -> {
                    try {
                        Integer.parseInt(item.substring(0, item.indexOf(':')));
                        return true;
                    } catch (Exception e) {
                        log.debug("Incorrect generated value: {}", item);
                        return false;
                    }
                })
                .collect(
                    Collectors.toMap(
                        // Maps each value (ex. 1:Other) like key='1' and value='Other'
                        key -> Integer.parseInt(key.substring(0, key.indexOf(':'))),
                        value -> value.substring(value.indexOf(':') + 1))
                )
            );

        }

        return Mono.error(new IncorrectCategorizationResponseException("Could not parse response"));
    }

    /**
     * Map categorized items from numbers to name from receipt.
     *
     * @param itemsMapping item name with its number, that sending to categorization.
     * @param categorizedItems item number with mapped category.
     * @param categories map all {@link TransactionCategory} with its names {@link TransactionCategory#getCategoryName()}
     * @return map with item raw name from receipt with mapped {@link TransactionCategory}.
     */
    private Mono<Map<String, TransactionCategory>> mapItemsWithCategories(
        Map<Integer, String> itemsMapping, Map<Integer, String> categorizedItems, HashMap<String,
        TransactionCategory> categories) {
        return categoriesService.getDefaultCategory()
            .flatMap(defaultCategory -> {
                HashMap<String, TransactionCategory> result = new HashMap<>();
                itemsMapping.forEach((key, value) -> {
                    TransactionCategory category = categories.getOrDefault(
                        categorizedItems.getOrDefault(key, "UNMAPPED"), defaultCategory);
                    result.put(value, category);
                });
                return Mono.just(result);
            });
    }
}
