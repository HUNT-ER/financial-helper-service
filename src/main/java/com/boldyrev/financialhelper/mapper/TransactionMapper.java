package com.boldyrev.financialhelper.mapper;

import com.boldyrev.financialhelper.dto.TransactionDto;
import com.boldyrev.financialhelper.model.Transaction;
import org.mapstruct.Mapper;

/**
 * Mapper for {@link Transaction}.
 *
 * @author Alexandr Boldyrev
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toEntity(TransactionDto transactionDto);
}
