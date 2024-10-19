package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.financialhelper.config.QrCodeReceiptApiProperties;
import com.boldyrev.financialhelper.dto.QrCodeReceiptMessageDto;
import com.boldyrev.financialhelper.dto.request.ReceiptQrRequestDto;
import com.boldyrev.financialhelper.dto.response.ReceiptQrResponseDto;
import com.boldyrev.financialhelper.exception.QrCodeReadingException;
import com.boldyrev.financialhelper.exception.ReceiptAlreadyExistsException;
import com.boldyrev.financialhelper.mapper.ReceiptMapper;
import com.boldyrev.financialhelper.model.Receipt;
import com.boldyrev.financialhelper.model.ReceiptQrData;
import com.boldyrev.financialhelper.repository.ReceiptsRepository;
import com.boldyrev.financialhelper.service.QrCodeService;
import com.boldyrev.financialhelper.service.ReceiptsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Implementation of {@link ReceiptsService}.
 *
 * @author Alexandr Boldyrev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReceiptsServiceImpl implements ReceiptsService {

    private final WebClient webClient;

    private final QrCodeReceiptApiProperties receiptApiProperties;

    private final ReceiptsRepository receiptsRepository;

    private final ReceiptMapper receiptMapper;

    private final QrCodeService qrCodeService;

    @Override
    public void processQrCodeReceipt(QrCodeReceiptMessageDto receiptDto) {
        qrCodeService.readQrCode(receiptDto.getQrCodeImage())
            .flatMap(this::sendQrReceiptOnDecode)
            .map(receiptMapper::mapFromReceiptQrResponse)
            .doOnNext(receipt -> receipt.setUserId(receiptDto.getUserId()))
            .flatMap(this::throwIfExists)
            .flatMap(this::save)
            .doOnError(ReceiptAlreadyExistsException.class,
                ex -> log.error("Existed receipt: %s".formatted(ex.getMessage()), ex))
            .doOnError(QrCodeReadingException.class,
                ex -> log.error("Bad QR: %s".formatted(ex.getMessage()), ex))
            .subscribe();
    }

    Mono<Receipt> throwIfExists(Receipt receipt) {
        ReceiptQrData qrData = receipt.getReceiptQrData();
        return receiptsRepository.findByUserQrData(
                receipt.getUserId(),
                qrData.getFn(),
                qrData.getFd(),
                qrData.getFp(),
                qrData.getCheckTime(),
                qrData.getType(),
                qrData.getSum())
            .flatMap(foundReceipt -> {
                String message = "Receipt already exists: userId:%s, fn:%s, fd:%s, fp:%s, time:%s, type:%s, sum:%s."
                    .formatted(
                        receipt.getUserId(),
                        qrData.getFn(),
                        qrData.getFd(),
                        qrData.getFp(),
                        qrData.getCheckTime(),
                        qrData.getType(),
                        qrData.getSum());
                log.info(message);

                return foundReceipt != null
                    ? Mono.error(new ReceiptAlreadyExistsException(message))
                    : Mono.just(receipt);
            })
            .switchIfEmpty(Mono.just(receipt));
    }

    Mono<Receipt> save(Receipt receipt) {
        log.debug("New receipt successfully saved: userId:{}, receiptQrData:{}", receipt.getUserId(),
            receipt.getReceiptQrData());

        return receiptsRepository.save(receipt);
    }

    private Mono<ReceiptQrResponseDto> sendQrReceiptOnDecode(String qrCode) {
        log.debug("Sending receipt QR code on decode: qrCode:{}", qrCode);

        return webClient.post()
            .uri(receiptApiProperties.getReadingApiUrl())
            .bodyValue(new ReceiptQrRequestDto(qrCode, receiptApiProperties.getReadingApiToken()))
            .retrieve()
            .bodyToMono(ReceiptQrResponseDto.class);
    }
}