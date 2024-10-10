package com.boldyrev.financialhelper.controller;

import com.boldyrev.controller.QuotesApi;
import com.boldyrev.dto.QuoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController implements QuotesApi {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public Mono<ResponseEntity<QuoteDto>> getQuote(String symbol, ServerWebExchange exchange) {

        rabbitTemplate.convertAndSend("common", "Test message");

        return null;
    }
}
