package com.boldyrev.financialhelper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
@Data
public class ReceiptQrData {

    @JsonProperty("qrRaw")
    private String qrRaw;

    @JsonProperty("fn")
    private String fn;

    @JsonProperty("fd")
    private String fd;

    @JsonProperty("fp")
    private String fp;

    @JsonProperty("checkTime")
    private String checkTime;

    @JsonProperty("type")
    private String type;

    @JsonProperty("sum")
    private String sum;
}
