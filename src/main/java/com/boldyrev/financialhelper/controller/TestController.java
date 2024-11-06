package com.boldyrev.financialhelper.controller;

import com.boldyrev.controller.QuotesApi;
import com.boldyrev.dto.QuoteDto;
import com.boldyrev.financialhelper.dto.QrCodeReceiptMessageDto;
import com.boldyrev.financialhelper.service.ReceiptsService;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController implements QuotesApi {

    private final RabbitTemplate rabbitTemplate;

    private final ReceiptsService receiptsService;

    @Override
    public Mono<ResponseEntity<QuoteDto>> getQuote(String symbol, ServerWebExchange exchange) {

        rabbitTemplate.convertAndSend("common", "Test message");

        QrCodeReceiptMessageDto qrCodeReceiptMessageDto = new QrCodeReceiptMessageDto();
        qrCodeReceiptMessageDto.setUserId(1L);

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(
            new FileInputStream("qr.jpg"))) {
            qrCodeReceiptMessageDto.setQrCodeImage(bufferedInputStream.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiptsService.processQrCodeReceipt(qrCodeReceiptMessageDto)
            .thenReturn(ResponseEntity.ok(null));
    }
}
