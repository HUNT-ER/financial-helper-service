package com.boldyrev.financialhelper.service;

import com.boldyrev.financialhelper.dto.QrCodeReceiptMessageDto;
import reactor.core.publisher.Mono;

/**
 * Service with business logic to process QR-code receipts.
 *
 * @author Alexandr Boldyrev
 */
public interface ReceiptsService {

    /**
     * Process QR-code receipt.
     *
     * @param receiptDto message with QR-code receipt.
     * @return void
     */
    Mono<Void> processQrCodeReceipt(QrCodeReceiptMessageDto receiptDto);
}
