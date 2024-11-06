package com.boldyrev.financialhelper.mapper;

import com.boldyrev.financialhelper.dto.HtmlReceiptAttributes;
import com.boldyrev.financialhelper.dto.response.ReceiptQrResponseDto;
import com.boldyrev.financialhelper.dto.response.ReceiptQrResponseDto.ReceiptDataDto.ReceiptJsonDataDto;
import com.boldyrev.financialhelper.dto.response.ReceiptQrResponseDto.ReceiptDataDto.ReceiptJsonDataDto.MetadataDto;
import com.boldyrev.financialhelper.dto.response.ReceiptQrResponseDto.ReceiptDataDto.ReceiptJsonDataDto.ReceiptItemDto;
import com.boldyrev.financialhelper.dto.response.ReceiptQrResponseDto.ReceiptRequestDto;
import com.boldyrev.financialhelper.model.Receipt;
import com.boldyrev.financialhelper.model.ReceiptData;
import com.boldyrev.financialhelper.model.ReceiptItem;
import com.boldyrev.financialhelper.model.ReceiptMetadata;
import com.boldyrev.financialhelper.model.ReceiptQrData;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for {@link Receipt}.
 *
 * @author Alexandr Boldyrev
 */
@Mapper(componentModel = "spring", imports = {LocalDateTime.class, ZoneOffset.class, Instant.class})
public interface ReceiptMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "receiptData", expression = "java(mapFromReceiptJsonDataDto(response.getData().getJson()))")
    @Mapping(target = "receiptQrData", expression = "java(mapFromReceiptRequestDto(response.getRequest()))")
    Receipt mapFromReceiptQrResponse(ReceiptQrResponseDto response);

    @Mapping(target = "nds10", qualifiedByName = "intToDouble")
    @Mapping(target = "totalSum", qualifiedByName = "intToDouble")
    @Mapping(target = "creditSum", qualifiedByName = "intToDouble")
    @Mapping(target = "prepaidSum", qualifiedByName = "intToDouble")
    @Mapping(target = "cashTotalSum", qualifiedByName = "intToDouble")
    @Mapping(target = "provisionSum", qualifiedByName = "intToDouble")
    @Mapping(target = "ecashTotalSum", qualifiedByName = "intToDouble")
    @Mapping(target = "userInn", qualifiedByName = "trim")
    @Mapping(target = "kktRegId", qualifiedByName = "trim")
    @Mapping(target = "dateTime", expression = "java(LocalDateTime.parse(jsonData.getDateTime()).toInstant(ZoneOffset.UTC))")
    ReceiptData mapFromReceiptJsonDataDto(ReceiptJsonDataDto jsonData);

    @Mapping(target = "fn", source = "requestDataDto.manual.fn")
    @Mapping(target = "fd", source = "requestDataDto.manual.fd")
    @Mapping(target = "fp", source = "requestDataDto.manual.fp")
    @Mapping(target = "checkTime", source = "requestDataDto.manual.checkTime")
    @Mapping(target = "type", source = "requestDataDto.manual.type")
    @Mapping(target = "sum", source = "requestDataDto.manual.sum")
    @Mapping(target = "qrRaw", source = "requestDataDto.qrRaw")
    ReceiptQrData mapFromReceiptRequestDto(ReceiptRequestDto requestDataDto);

    @Mapping(target = "sum", qualifiedByName = "intToDouble")
    @Mapping(target = "price", qualifiedByName = "intToDouble")
    ReceiptItem mapFromReceiptItemDto(ReceiptItemDto receiptItemDto);

    @Mapping(target = "receiveDate", expression = "java(Instant.parse(metadataDto.getReceiveDate()))")
    ReceiptMetadata mapFromMetadataDto(MetadataDto metadataDto);

    @Mapping(target = "organization", source = "receipt.receiptData.user")
    @Mapping(target = "organizationAddress", source = "receipt.receiptData.retailPlaceAddress")
    @Mapping(target = "organizationInn", source = "receipt.receiptData.userInn")
    @Mapping(target = "receiptDate", source = "receipt.receiptData.dateTime")
    @Mapping(target = "items", source = "receipt.receiptData.items")
    @Mapping(target = "totalSum", source = "receipt.receiptData.totalSum")
    @Mapping(target = "cashTotalSum", source = "receipt.receiptData.cashTotalSum")
    @Mapping(target = "ecashTotalSum", source = "receipt.receiptData.ecashTotalSum")
    @Mapping(target = "fn", source = "receipt.receiptQrData.fn")
    @Mapping(target = "fd", source = "receipt.receiptQrData.fd")
    @Mapping(target = "fp", source = "receipt.receiptQrData.fp")
    HtmlReceiptAttributes mapToHtmlReceiptAttributes(Receipt receipt);

    @Named("intToDouble")
    default double intToDouble(int value) {
        return value * 1.00 / 100;
    }

    @Named("trim")
    default String intToDouble(String value) {
        return value.trim();
    }
}
