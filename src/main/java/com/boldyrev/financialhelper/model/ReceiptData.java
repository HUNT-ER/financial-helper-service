package com.boldyrev.financialhelper.model;

import com.boldyrev.financialhelper.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

/**
 * Representation of receipt document data, including items and metadata.
 *
 * @author Alexandr Boldyrev
 */
@Data
public class ReceiptData {

    @JsonProperty("code")
    private int code;

    @JsonProperty("user")
    private String user;

    @JsonProperty("items")
    private List<ReceiptItem> items;

    @JsonProperty("nds10")
    private double nds10;

    @JsonProperty("fnsUrl")
    private String fnsUrl;

    @JsonProperty("region")
    private String region;

    @JsonProperty("userInn")
    private String userInn;

    @JsonProperty("dateTime")
    private String dateTime;

    @JsonProperty("kktRegId")
    private String kktRegId;

    @JsonProperty("metadata")
    private ReceiptMetadata metadata;

    @JsonProperty("operator")
    private String operator;

    @JsonProperty("totalSum")
    private double totalSum;

    @JsonProperty("creditSum")
    private double creditSum;

    @JsonProperty("numberKkt")
    private String numberKkt;

    @JsonProperty("fiscalSign")
    private long fiscalSign;

    @JsonProperty("prepaidSum")
    private double prepaidSum;

    @JsonProperty("retailPlace")
    private String retailPlace;

    @JsonProperty("shiftNumber")
    private int shiftNumber;

    @JsonProperty("cashTotalSum")
    private double cashTotalSum;

    @JsonProperty("provisionSum")
    private double provisionSum;

    @JsonProperty("ecashTotalSum")
    private double ecashTotalSum;

    @JsonProperty("operationType")
    private OperationType operationType;

    @JsonProperty("redefineMask")
    private int redefineMask;

    @JsonProperty("requestNumber")
    private int requestNumber;

    @JsonProperty("sellerAddress")
    private String sellerAddress;

    @JsonProperty("fiscalDriveNumber")
    private String fiscalDriveNumber;

    @JsonProperty("messageFiscalSign")
    private double messageFiscalSign;

    @JsonProperty("retailPlaceAddress")
    private String retailPlaceAddress;

    @JsonProperty("appliedTaxationType")
    private int appliedTaxationType;

    @JsonProperty("buyerPhoneOrAddress")
    private String buyerPhoneOrAddress;

    @JsonProperty("fiscalDocumentNumber")
    private int fiscalDocumentNumber;

    @JsonProperty("fiscalDocumentFormatVer")
    private int fiscalDocumentFormatVer;

    @JsonProperty("checkingLabeledProdResult")
    private int checkingLabeledProdResult;
}