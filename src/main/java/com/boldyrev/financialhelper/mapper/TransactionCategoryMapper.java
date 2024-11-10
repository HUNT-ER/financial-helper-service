package com.boldyrev.financialhelper.mapper;

import com.boldyrev.dto.TransactionCategoriesResponse;
import com.boldyrev.dto.TransactionCategoryResponseDto;
import com.boldyrev.financialhelper.dto.TransactionCategoryDto;
import com.boldyrev.financialhelper.model.TransactionCategory;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper of {@link TransactionCategory}.
 *
 * @author Alexandr Boldyrev
 */
@Mapper(componentModel = "spring")
public interface TransactionCategoryMapper {

    TransactionCategoryDto toDto(TransactionCategory transactionCategory);

    @Mapping(target = "isExpense", source = "expense")
    TransactionCategoryResponseDto toTransactionCategoryResponseDto(
        TransactionCategoryDto category);

    List<TransactionCategoryResponseDto> toListTransactionCategoryResponseDto(
        List<TransactionCategoryDto> categories);

    default TransactionCategoriesResponse toTransactionCategoriesResponse(
        List<TransactionCategoryDto> categories) {
        return new TransactionCategoriesResponse(toListTransactionCategoryResponseDto(categories));
    }
}
