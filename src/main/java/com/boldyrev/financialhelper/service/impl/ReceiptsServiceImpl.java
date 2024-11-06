package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.financialhelper.config.QrCodeReceiptApiProperties;
import com.boldyrev.financialhelper.dto.MessageDto;
import com.boldyrev.financialhelper.dto.QrCodeReceiptMessageDto;
import com.boldyrev.financialhelper.dto.TransactionCategoryDto;
import com.boldyrev.financialhelper.dto.TransactionDto;
import com.boldyrev.financialhelper.dto.request.ReceiptQrRequestDto;
import com.boldyrev.financialhelper.dto.response.ReceiptQrResponseDto;
import com.boldyrev.financialhelper.enums.MessageType;
import com.boldyrev.financialhelper.enums.RoutingKey;
import com.boldyrev.financialhelper.enums.TransactionType;
import com.boldyrev.financialhelper.enums.UserMessage;
import com.boldyrev.financialhelper.exception.IncorrectCategorizationResponseException;
import com.boldyrev.financialhelper.exception.QrCodeReadingException;
import com.boldyrev.financialhelper.exception.ReceiptAlreadyExistsException;
import com.boldyrev.financialhelper.mapper.ReceiptMapper;
import com.boldyrev.financialhelper.model.Receipt;
import com.boldyrev.financialhelper.model.ReceiptItem;
import com.boldyrev.financialhelper.model.ReceiptQrData;
import com.boldyrev.financialhelper.repository.ReceiptsRepository;
import com.boldyrev.financialhelper.service.HtmlTemplatesService;
import com.boldyrev.financialhelper.service.QrCodeService;
import com.boldyrev.financialhelper.service.RabbitMqMessageService;
import com.boldyrev.financialhelper.service.ReceiptItemsCategorizationService;
import com.boldyrev.financialhelper.service.ReceiptsService;
import com.boldyrev.financialhelper.service.TransactionsService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

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

    private final ReceiptItemsCategorizationService categorizationService;

    private final RabbitMqMessageService messageService;

    private final TransactionsService transactionsService;

    private final TransactionalOperator mongoTransactionalOperator;

    private final TransactionalOperator r2dbcTransactionalOperator;

    private final HtmlTemplatesService templatesService;

    @Override
    public Mono<Void> processQrCodeReceipt(QrCodeReceiptMessageDto receiptDto) {
        return qrCodeService.readQrCode(receiptDto.getQrCodeImage())
            .flatMap(this::sendQrReceiptOnDecode)
            .map(receiptMapper::mapFromReceiptQrResponse)
            .doOnNext(receipt -> receipt.setUserId(receiptDto.getUserId()))
            .flatMap(this::throwIfExists)
            .flatMap(this::save)
            .flatMap(receipt ->
                categorizationService.categorizeItems(receipt.getReceiptData().getItems())
                    .flatMap(categorizedItems -> Mono.just(Tuples.of(receipt, categorizedItems))))
            .flatMap(tuple -> saveTransactions(tuple.getT1(), tuple.getT2()))
            .flatMap(receipt -> templatesService.formHtmlReceipt(
                receiptMapper.mapToHtmlReceiptAttributes(receipt)))
            .flatMap(htmlReceipt -> sendHtmlReceipt(receiptDto.getUserId(), htmlReceipt))
            .doOnError(ex -> processException(ex, receiptDto.getUserId()))
            .as(mongoTransactionalOperator::transactional)
            .as(r2dbcTransactionalOperator::transactional)
            .then();
    }

    private Mono<Receipt> throwIfExists(Receipt receipt) {
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

    private Mono<Receipt> save(Receipt receipt) {
        log.debug("New receipt successfully saved: userId:{}, receiptQrData:{}",
            receipt.getUserId(),
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

    private Mono<Receipt> saveTransactions(Receipt receipt,
        Map<String, TransactionCategoryDto> categorizedItems) {
        TransactionType transactionType = receipt.getReceiptData().getOperationType()
            .getTransactionType();

        List<TransactionDto> mappedTransactions = new ArrayList<>();

        receipt.getReceiptData()
            .getItems()
            .stream()
            .collect(Collectors.groupingBy(item -> categorizedItems.get(item.getName()).getId(),
                Collectors.summarizingDouble(ReceiptItem::getSum))).forEach(
                (categoryId, amount) -> mappedTransactions.add(
                    TransactionDto.builder()
                        .categoryId(categoryId)
                        .amount(amount.getSum())
                        .userId(receipt.getUserId())
                        .transactionType(transactionType)
                        .transactionDateTime(receipt.getReceiptData().getDateTime())
                        .build()
                )
            );

        return transactionsService.saveTransactions(mappedTransactions).thenReturn(receipt);
    }

    private void processException(Throwable ex, Long userId) {
        log.debug(ex.getMessage());
        MessageDto messageDto = new MessageDto(userId, null, MessageType.ERROR, null);
        switch (ex) {
            case ReceiptAlreadyExistsException e ->
                messageDto.setMessage(UserMessage.RECEIPT_ALREADY_EXISTS);
            case QrCodeReadingException e ->
                messageDto.setMessage(UserMessage.INCORRECT_QR_CODE);
            case IncorrectCategorizationResponseException e ->
                messageDto.setMessage(UserMessage.CATEGORIZATION_ERROR);
            default -> {
                return;
            }
        }
        messageService.sendMessage(RoutingKey.ERROR, messageDto);
    }

    private Mono<Void> sendHtmlReceipt(Long userId, String htmlReceipt) {
        return Mono.fromRunnable(() -> {
            MessageDto message = new MessageDto(userId, UserMessage.SUCCESS_QR_CODE_READING,
                MessageType.HTML_RECEIPT, htmlReceipt);
            messageService.sendMessage(RoutingKey.HTML_RECEIPT, message);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}
