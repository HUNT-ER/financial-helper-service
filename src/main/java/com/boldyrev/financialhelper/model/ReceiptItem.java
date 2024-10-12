package com.boldyrev.financialhelper.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Representation of receipt document item.
 *
 * @author Alexandr Boldyrev
 */
@Data
public class ReceiptItem {

    @JsonProperty("nds")
    private int nds;

    @JsonProperty("sum")
    private double sum;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private double price;

    @JsonProperty("quantity")
    private double quantity;

    @JsonProperty("paymentType")
    private int paymentType;

    @JsonProperty("productType")
    private int productType;

    @JsonProperty("labelCodeProcessMode")
    private int labelCodeProcessMode;

    @JsonProperty("itemsQuantityMeasure")
    private int itemsQuantityMeasure;

    @JsonProperty("checkingProdInformationResult")
    private int checkingProdInformationResult;
}
