package com.boldyrev.financialhelper.mapper;

import com.boldyrev.financialhelper.dto.TransactionCategoryDto;
import com.boldyrev.financialhelper.model.TransactionCategory;
import org.mapstruct.Mapper;

/**
 * Mapper of {@link TransactionCategory}.
 *
 * @author Alexandr Boldyrev
 */
@Mapper(componentModel = "spring")
public interface TransactionCategoryMapper {

    TransactionCategoryDto toDto(TransactionCategory transactionCategory);
}
