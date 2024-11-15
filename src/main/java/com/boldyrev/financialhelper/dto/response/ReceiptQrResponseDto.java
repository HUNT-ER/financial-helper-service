package com.boldyrev.financialhelper.dto.response;

import com.boldyrev.financialhelper.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for {@code proverkacheka.com} API.
 *
 * @author Alexandr Boldyrev
 */
@Data
@NoArgsConstructor
public class ReceiptQrResponseDto {

    @JsonProperty("code")
    private int code;

    @JsonProperty("first")
    private int first;

    @JsonProperty("data")
    private ReceiptDataDto data;

    @Data
    @NoArgsConstructor
    public static class ReceiptDataDto {

        @JsonProperty("json")
        private ReceiptJsonDataDto json;

        @JsonProperty("html")
        private String html;

        @Data
        @NoArgsConstructor
        public static class ReceiptJsonDataDto {

            @JsonProperty("code")
            private int code;

            @JsonProperty("user")
            private String user;

            @JsonProperty("items")
            private List<ReceiptItemDto> items;

            @JsonProperty("nds10")
            private int nds10;

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
            private MetadataDto metadata;

            @JsonProperty("operator")
            private String operator;

            @JsonProperty("totalSum")
            private int totalSum;

            @JsonProperty("creditSum")
            private int creditSum;

            @JsonProperty("numberKkt")
            private String numberKkt;

            @JsonProperty("fiscalSign")
            private long fiscalSign;

            @JsonProperty("prepaidSum")
            private int prepaidSum;

            @JsonProperty("retailPlace")
            private String retailPlace;

            @JsonProperty("shiftNumber")
            private int shiftNumber;

            @JsonProperty("cashTotalSum")
            private int cashTotalSum;

            @JsonProperty("provisionSum")
            private int provisionSum;

            @JsonProperty("ecashTotalSum")
            private int ecashTotalSum;

            @JsonProperty("operationType")
            private OperationType operationType;

            @JsonProperty("redefine_mask")
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

            @Data
            @NoArgsConstructor
            public static class ReceiptItemDto {

                @JsonProperty("nds")
                private int nds;

                @JsonProperty("sum")
                private int sum;

                @JsonProperty("name")
                private String name;

                @JsonProperty("price")
                private int price;

                @JsonProperty("quantity")
                private int quantity;

                @JsonProperty("paymentType")
                private int paymentType;

                @JsonProperty("productType")
                private int productType;

                @JsonProperty("productCodeNew")
                private ProductCodeNewDto productCodeNew;

                @JsonProperty("labelCodeProcesMode")
                private int labelCodeProcessMode;

                @JsonProperty("itemsIndustryDetails")
                private List<ItemsIndustryDetailsDto> itemsIndustryDetails;

                @JsonProperty("itemsQuantityMeasure")
                private int itemsQuantityMeasure;

                @JsonProperty("checkingProdInformationResult")
                private int checkingProdInformationResult;

                @Data
                @NoArgsConstructor
                public static class ProductCodeNewDto {

                    @JsonProperty("gs1m")
                    private Gs1mDto gs1m;

                    public static class Gs1mDto {

                        @JsonProperty("gtin")
                        private String gtin;

                        @JsonProperty("sernum")
                        private String sernum;

                        @JsonProperty("productIdType")
                        private int productIdType;

                        @JsonProperty("rawProductCode")
                        private String rawProductCode;
                    }
                }

                @Data
                @NoArgsConstructor
                public static class ItemsIndustryDetailsDto {

                    @JsonProperty("idFoiv")
                    private String idFoiv;

                    @JsonProperty("industryPropValue")
                    private String industryPropValue;

                    @JsonProperty("foundationDocNumber")
                    private String foundationDocNumber;

                    @JsonProperty("foundationDocDateTime")
                    private String foundationDocDateTime;
                }
            }

            @Data
            @NoArgsConstructor
            public static class MetadataDto {

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
        }
    }

    @JsonProperty("request")
    private ReceiptRequestDto request;

    @Data
    @NoArgsConstructor
    public static class ReceiptRequestDto {

        @JsonProperty("qrurl")
        private String qrUrl;

        @JsonProperty("qrfile")
        private String qrFile;

        @JsonProperty("qrraw")
        private String qrRaw;

        @JsonProperty("manual")
        private ManualDto manual;

        @Data
        @NoArgsConstructor
        public static class ManualDto {

            @JsonProperty("fn")
            private String fn;

            @JsonProperty("fd")
            private String fd;

            @JsonProperty("fp")
            private String fp;

            @JsonProperty("check_time")
            private String checkTime;

            @JsonProperty("type")
            private String type;

            @JsonProperty("sum")
            private String sum;
        }
    }
}
