package com.boldyrev.financialhelper.service;

import com.boldyrev.financialhelper.enums.RoutingKey;

/**
 * Service for sending messages;
 *
 * @author Alexandr Boldyrev
 */
public interface RabbitMqMessageService {

    <T> void sendMessage(RoutingKey routingKey, T message);
}
