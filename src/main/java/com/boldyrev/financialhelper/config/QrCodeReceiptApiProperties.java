package com.boldyrev.financialhelper.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Properties for reading qr-code receipt API.
 *
 * @author Alexandr Boldyrev
 */
@Getter
@Component
public class QrCodeReceiptApiProperties {

    @Value("${app.qr-receipt.reading-api-url}")
    private String readingApiUrl;

    @Value("${app.qr-receipt.reading-api-token}")
    private String readingApiToken;
}
