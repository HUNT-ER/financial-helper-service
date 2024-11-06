package com.boldyrev.financialhelper.service.impl;

import com.boldyrev.financialhelper.enums.RoutingKey;
import com.boldyrev.financialhelper.service.RabbitMqMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * Implementation of RabbitMqMessageService.
 *
 * @author Alexandr Boldyrev
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqMessageServiceImpl implements RabbitMqMessageService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public <T> void sendMessage(RoutingKey routingKey, T message) {
        rabbitTemplate.convertAndSend(routingKey.name(), message);
    }
}
