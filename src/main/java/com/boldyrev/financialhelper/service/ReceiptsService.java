package com.boldyrev.financialhelper.service;

import com.boldyrev.financialhelper.dto.QrCodeReceiptDto;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
public interface ReceiptsService {

    void processQrCodeReceipt(QrCodeReceiptDto receiptDto);
}
