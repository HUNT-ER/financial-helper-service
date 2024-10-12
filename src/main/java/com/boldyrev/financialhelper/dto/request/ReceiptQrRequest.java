package com.boldyrev.financialhelper.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
public record ReceiptQrRequest(@JsonProperty("qrraw") String qrRaw, String token) {
}
