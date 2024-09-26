package com.example.spring.consumer.amqp.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.spring.consumer.amqp.AmqpRePublish;

@Component
public class RePublishRabbitMq implements AmqpRePublish {

    private static final String X_RETRIES_HEADER = "x-retries";

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String queue;
    private final String deadLetter;
    private final String parkingLot;

    public RePublishRabbitMq(RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.request.exchenge.producer}") String exchange,
            @Value("${spring.rabbitmq.request.routing-key.producer}") String queue,
            @Value("${spring.rabbitmq.request.dead-letter.producer}") String deadLetter,
            @Value("${spring.rabbitmq.request.parking-lot.producer}") String parkingLot) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.queue = queue;
        this.deadLetter = deadLetter;
        this.parkingLot = parkingLot;
    }

    @Override
    @Scheduled(cron = "${spring.rabbitmq.listener.time-retry}")
    public void rePublish() {
        List<Message> messages = getQueueMessages();

        messages.forEach(message -> {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            Integer retriesHeader = (Integer) headers.get(X_RETRIES_HEADER);

            if (retriesHeader == null) {
                retriesHeader = 0;
            }

            if (retriesHeader < 3) {
                headers.put(X_RETRIES_HEADER, retriesHeader + 1);
                rabbitTemplate.send(exchange, queue, message);
            } else {
                rabbitTemplate.send(parkingLot, message);
            }
        });
    }

    private List<Message> getQueueMessages() {
        List<Message> messages = new ArrayList<>();
        Boolean isNull;
        Message message;

        do {
            message = rabbitTemplate.receive(deadLetter);
            isNull = message != null;

            if (Boolean.TRUE.equals(isNull)) {
                messages.add(message);
            }
        } while (Boolean.TRUE.equals(isNull));

        return messages;
    }

}
