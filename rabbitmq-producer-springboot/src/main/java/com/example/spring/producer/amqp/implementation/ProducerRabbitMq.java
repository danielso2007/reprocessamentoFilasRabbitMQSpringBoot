package com.example.spring.producer.amqp.implementation;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.example.spring.producer.amqp.AmqpProducer;
import com.example.spring.producer.dto.MessageQueue;

@Component
public class ProducerRabbitMq implements AmqpProducer<MessageQueue> {

    private final RabbitTemplate rabbitTemplate;
    private final String queue;
    private final String exchange;

    public ProducerRabbitMq(RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.request.routing-key.producer}") String queue,
            @Value("${spring.rabbitmq.request.exchenge.producer}") String exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
        this.exchange = exchange;
    }

    @Override
    public void producer(MessageQueue message) {
        try {
            rabbitTemplate.convertAndSend(exchange, queue, message);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException(ex);
        }
    }
}
