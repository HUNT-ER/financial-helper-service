package com.boldyrev.financialhelper.mapper;

import com.boldyrev.dto.SpendingLimitCreationDto;
import com.boldyrev.dto.SpendingLimitResponseDto;
import com.boldyrev.dto.SpendingLimitsResponse;
import com.boldyrev.financialhelper.model.SpendingLimit;
import com.boldyrev.financialhelper.repository.projection.SpendingLimitCategoryProjection;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

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
    SpendingLimit updateSpendingLimit(@MappingTarget SpendingLimit limit,
        SpendingLimitCreationDto dto);

    @Mapping(target = "startPeriod", qualifiedByName = "toOffsetDateTime")
    @Mapping(target = "endPeriod", qualifiedByName = "toOffsetDateTime")
    SpendingLimitResponseDto toSpendingLimitResponse(SpendingLimitCategoryProjection limit);

    List<SpendingLimitResponseDto> toListSpendingLimitResponseDto(
        List<SpendingLimitCategoryProjection> limits);

    @Named("toOffsetDateTime")
    default OffsetDateTime toOffsetDateTime(Instant instant) {
        return OffsetDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    default SpendingLimitsResponse toSpendingLimitsResponse(
        List<SpendingLimitCategoryProjection> limits) {
        return new SpendingLimitsResponse(toListSpendingLimitResponseDto(limits));
    }
}
