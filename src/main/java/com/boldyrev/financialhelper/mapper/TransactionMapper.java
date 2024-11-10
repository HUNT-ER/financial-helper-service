package com.boldyrev.financialhelper.mapper;

import com.boldyrev.dto.TransactionCreationDto;
import com.boldyrev.dto.TransactionResponseDto;
import com.boldyrev.dto.TransactionsResponse;
import com.boldyrev.financialhelper.dto.TransactionDto;
import com.boldyrev.financialhelper.model.Transaction;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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

    @Mapping(target = "transactionDateTime", qualifiedByName = "toOffsetDateTime")
    TransactionResponseDto toTransactionResponseDto(Transaction transaction);

    List<TransactionResponseDto> toListTransactionResponseDto(List<Transaction> transactions);

    default TransactionsResponse toTransactionsResponse(List<Transaction> transactions) {
        return new TransactionsResponse(toListTransactionResponseDto(transactions));
    }

    @Named("toOffsetDateTime")
    default OffsetDateTime toOffsetDateTime(Instant instant) {
        return OffsetDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
