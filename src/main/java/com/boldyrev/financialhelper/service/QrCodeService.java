package com.boldyrev.financialhelper.service;

import reactor.core.publisher.Mono;

/**
 * Service for process QR-codes.
 *
 * @author Alexandr Boldyrev
 */
public interface QrCodeService {

    /**
     * Read text of QR-code.
     *
     * @param bytes byte array with QR-code image
     * @return read text from QR-code
     */
    Mono<String> readQrCode(byte[] bytes);
}
