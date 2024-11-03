package com.boldyrev.financialhelper.service;

import reactor.core.publisher.Mono;

/**
 * Service for authorization in external API.
 *
 * @author Alexandr Boldyrev
 */
public interface AuthorizationService {

    Mono<String> getToken();
}
