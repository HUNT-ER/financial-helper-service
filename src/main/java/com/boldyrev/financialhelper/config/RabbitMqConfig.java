package com.boldyrev.financialhelper.config;

import com.boldyrev.financialhelper.enums.RoutingKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ configuration.
 *
 * @author Alexandr Boldyrev
 */
@Configuration
public class RabbitMqConfig {

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.queue.error}")
    private String errorQueue;

    @Value("${app.rabbitmq.queue.html-receipt}")
    private String htmlReceiptQueue;

    @Value("${app.rabbitmq.queue.notification}")
    private String notificationQueue;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, ObjectMapper mapper) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter(mapper));
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue errorQueue() {
        return new Queue(errorQueue);
    }

    @Bean
    public Binding errorQueueBinding() {
        return BindingBuilder.bind(errorQueue())
            .to(directExchange())
            .with(RoutingKey.ERROR);
    }

    @Bean
    public Queue htmlReceiptQueue() {
        return new Queue(htmlReceiptQueue);
    }

    @Bean
    public Binding htmlReceiptQueueBinding() {
        return BindingBuilder.bind(htmlReceiptQueue())
            .to(directExchange())
            .with(RoutingKey.HTML_RECEIPT);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue);
    }

    @Bean
    public Binding notificationQueueBinding() {
        return BindingBuilder.bind(notificationQueue())
            .to(directExchange())
            .with(RoutingKey.NOTIFICATION);
    }
}
