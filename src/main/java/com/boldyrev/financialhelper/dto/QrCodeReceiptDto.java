package com.boldyrev.financialhelper.dto;

import lombok.Data;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
@Data
public class QrCodeReceiptDto {

    private Long userId;

    private byte[] qrCodeImage;

    private String extension;
}
