package com.boldyrev.financialhelper.mapper;

import com.boldyrev.dto.SpendingLimitCreationDto;
import com.boldyrev.financialhelper.model.SpendingLimit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper of {@link SpendingLimitMapper}.
 *
 * @author Alexandr Boldyrev
 */
@Mapper(componentModel = "spring")
public interface SpendingLimitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "startPeriod", expression = "java(dto.getStartPeriod().toInstant())")
    @Mapping(target = "endPeriod", expression = "java(dto.getEndPeriod().toInstant())")
    SpendingLimit fromSpendingLimitCreationDto(SpendingLimitCreationDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "categoryId", ignore = true)
    @Mapping(target = "startPeriod", expression = "java(dto.getStartPeriod().toInstant())")
    @Mapping(target = "endPeriod", expression = "java(dto.getEndPeriod().toInstant())")
    SpendingLimit updateSpendingLimit(@MappingTarget SpendingLimit limit, SpendingLimitCreationDto dto);
}
