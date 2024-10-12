package com.boldyrev.financialhelper.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request DTO for {@code proverkacheka.com} API.
 *
 * @author Alexandr Boldyrev
 */
public record ReceiptQrRequest(@JsonProperty("qrraw") String qrRaw, String token) {
}
