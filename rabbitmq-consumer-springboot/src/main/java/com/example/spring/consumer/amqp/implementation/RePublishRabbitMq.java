package com.example.spring.consumer.amqp.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.example.spring.consumer.amqp.AmqpRePublish;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RePublishRabbitMq implements AmqpRePublish {

    private static final String X_RETRIES_HEADER = "x-retries";
    private static final Logger logger = LoggerFactory.getLogger(RePublishRabbitMq.class);

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String queue;
    private final String deadLetter;
    private final String parkingLot;
    private final Integer retriesHeaderMax;

    public RePublishRabbitMq(RabbitTemplate rabbitTemplate,
            @Value("${spring.rabbitmq.request.exchenge.producer}") String exchange,
            @Value("${spring.rabbitmq.request.routing-key.producer}") String queue,
            @Value("${spring.rabbitmq.request.dead-letter.producer}") String deadLetter,
            @Value("${spring.rabbitmq.request.parking-lot.producer}") String parkingLot,
            @Value("${mensagens.tentativas.maxima}") Integer retriesHeaderMax) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.queue = queue;
        this.deadLetter = deadLetter;
        this.parkingLot = parkingLot;
        this.retriesHeaderMax = retriesHeaderMax;
    }

    @Override
    @Scheduled(cron = "${spring.rabbitmq.listener.time-retry}", zone = "America/Sao_Paulo")
    public void rePublish() {

        // Apenas para log... remover depois.
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        logger.info("Iniciando rePublish | {}...", formattedDateTime);

        List<Message> messages = getQueueMessages();

        if (messages.isEmpty()) {
            logger.info("Não há mensagens na fila para reprocessamento.");
        }

        messages.forEach(message -> {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            Integer retriesHeader = (Integer) headers.get(X_RETRIES_HEADER);

            if (retriesHeader == null) {
                retriesHeader = 0;
            }

            if (retriesHeader < this.retriesHeaderMax) {
                // Irá reenviar a mensagem com o header de tentativas aumentando
                headers.put(X_RETRIES_HEADER, retriesHeader + 1);
                logger.info("Enviando para nova tentativa.");
                rabbitTemplate.send(exchange, queue, message);
            } else {
                logger.info("Enviando para Parking Lot.");
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
