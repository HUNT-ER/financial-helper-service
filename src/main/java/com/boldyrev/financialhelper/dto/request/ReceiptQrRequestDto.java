package com.boldyrev.financialhelper.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request DTO for {@code proverkacheka.com} API.
 *
 * @author Alexandr Boldyrev
 */
public record ReceiptQrRequestDto(@JsonProperty("qrraw") String qrRaw,
                                  @JsonProperty("token") String token) {
}
