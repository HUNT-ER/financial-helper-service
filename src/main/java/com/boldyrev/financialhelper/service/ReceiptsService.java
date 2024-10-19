package com.boldyrev.financialhelper.service;

import com.boldyrev.financialhelper.dto.QrCodeReceiptMessageDto;

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
     */
    void processQrCodeReceipt(QrCodeReceiptMessageDto receiptDto);
}
