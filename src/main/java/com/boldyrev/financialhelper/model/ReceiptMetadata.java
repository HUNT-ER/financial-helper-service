package com.boldyrev.financialhelper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
@Data
public class ReceiptMetadata {

    @JsonProperty("id")
    private long id;

    @JsonProperty("ofdId")
    private String ofdId;

    @JsonProperty("address")
    private String address;

    @JsonProperty("subtype")
    private String subtype;

    @JsonProperty("receiveDate")
    private String receiveDate;
}


