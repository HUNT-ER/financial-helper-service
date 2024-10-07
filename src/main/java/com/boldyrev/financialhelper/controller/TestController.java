package com.boldyrev.financialhelper.controller;

import com.boldyrev.controller.QuotesApi;
import com.boldyrev.dto.QuoteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class TestController implements QuotesApi {

    @Override
    public Mono<ResponseEntity<QuoteDto>> getQuote(String symbol, ServerWebExchange exchange) {
        return QuotesApi.super.getQuote(symbol, exchange);
    }
}
