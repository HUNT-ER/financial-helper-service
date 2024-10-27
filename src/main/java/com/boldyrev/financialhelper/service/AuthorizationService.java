package com.boldyrev.financialhelper.service;

import reactor.core.publisher.Mono;

/**
 * //todo add description
 *
 * @author Alexandr Boldyrev
 */
public interface AuthorizationService {

    Mono<String> getToken();
}
