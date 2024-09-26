package com.example.spring.producer.service.implementation;

import org.springframework.stereotype.Service;
import com.example.spring.producer.amqp.AmqpProducer;
import com.example.spring.producer.dto.MessageQueue;
import com.example.spring.producer.service.AmqpService;

@Service
public class RabbitMqServiceImpl implements AmqpService {

    private final AmqpProducer<MessageQueue> amqp;

    public RabbitMqServiceImpl(AmqpProducer<MessageQueue> amqp) {
        this.amqp = amqp;
    }

    @Override
    public void sendToConsumer(MessageQueue message) {
        amqp.producer(message);
    }
}
