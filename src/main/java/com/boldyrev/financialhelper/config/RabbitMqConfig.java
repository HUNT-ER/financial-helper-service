package com.boldyrev.financialhelper.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.queue}")
    private String queue;

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchange);
        return rabbitTemplate;
    }

    @Bean
    public Queue commonQueue() {
        return new Queue(queue);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding commonBinding() {
        //todo enum as routingKey
        return BindingBuilder.bind(commonQueue())
            .to(directExchange())
            .with("common");
    }
}
