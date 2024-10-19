package com.boldyrev.financialhelper.dto;

import lombok.Data;

/**
 * Representation of telegram message with userId and QR-code image.
 *
 * @author Alexandr Boldyrev
 */
@Data
public class QrCodeReceiptMessageDto {

    /**
     * Telegram userId.
     */
    private Long userId;

    /**
     * Byte array with QR-code image.
     */
    private byte[] qrCodeImage;
}
