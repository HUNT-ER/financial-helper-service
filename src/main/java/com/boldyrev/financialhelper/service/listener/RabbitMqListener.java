package com.boldyrev.financialhelper.service.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMqListener {

    @RabbitListener(queues = "fm-common")
    public void listen(String message) {
        System.out.println(message);
    }
}