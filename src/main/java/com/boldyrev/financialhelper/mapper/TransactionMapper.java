package com.boldyrev.financialhelper.mapper;

import com.boldyrev.dto.TransactionCreationDto;
import com.boldyrev.financialhelper.dto.TransactionDto;
import com.boldyrev.financialhelper.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for {@link Transaction}.
 *
 * @author Alexandr Boldyrev
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toEntity(TransactionDto transactionDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "transactionDateTime", expression = "java(dto.getTransactionDateTime().toInstant())")
    Transaction fromTransactionCreationDto(TransactionCreationDto dto);
}
