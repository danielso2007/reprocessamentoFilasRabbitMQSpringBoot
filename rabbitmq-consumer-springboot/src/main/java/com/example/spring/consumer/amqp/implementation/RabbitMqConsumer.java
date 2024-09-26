package com.example.spring.consumer.amqp.implementation;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.example.spring.consumer.amqp.AmqpConsumer;
import com.example.spring.consumer.dto.MessageQueue;
import com.example.spring.consumer.service.ConsumerService;

@Component
public class RabbitMqConsumer implements AmqpConsumer<MessageQueue> {

    private final ConsumerService consumerService;

    public RabbitMqConsumer(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.request.routing-key.producer}")
    public void consumer(MessageQueue message) {
        try {
            consumerService.action(message);
        } catch (Exception ex) {
            throw new AmqpRejectAndDontRequeueException(ex);
        }
    }
}
