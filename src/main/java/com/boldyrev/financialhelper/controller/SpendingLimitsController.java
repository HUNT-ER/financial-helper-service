package com.boldyrev.financialhelper.controller;

import com.boldyrev.controller.SpendingLimitsApi;
import com.boldyrev.dto.SpendingLimitCreationDto;
import com.boldyrev.dto.SpendingLimitsResponse;
import com.boldyrev.financialhelper.mapper.SpendingLimitMapper;
import com.boldyrev.financialhelper.service.SpendingLimitsService;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Rest controller for operations with spending limits.
 *
 * @author Alexandr Boldyrev
 */
@RestController
@RequiredArgsConstructor
public class SpendingLimitsController implements SpendingLimitsApi {

    private final SpendingLimitsService spendingLimitsService;

    private final SpendingLimitMapper limitMapper;

    @Override
    public Mono<ResponseEntity<Void>> addSpendingLimit(
        Mono<SpendingLimitCreationDto> spendingLimitCreationDto, ServerWebExchange exchange) {
        return spendingLimitCreationDto.flatMap(spendingLimitsService::addSpendingLimit)
            .then(Mono.just(new ResponseEntity<>(HttpStatus.CREATED)));
    }

    @Override
    public Mono<ResponseEntity<SpendingLimitsResponse>> getAllUserLimits(Long userId,
        ServerWebExchange exchange) {
        return spendingLimitsService.getActiveLimits(userId, Instant.now())
            .collectList()
            .map(limitMapper::toSpendingLimitsResponse)
            .map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteLimit(Long userId, UUID categoryId,
        ServerWebExchange exchange) {
        return spendingLimitsService.deleteLimit(userId, categoryId)
            .thenReturn(ResponseEntity.ok().build());
    }

}
