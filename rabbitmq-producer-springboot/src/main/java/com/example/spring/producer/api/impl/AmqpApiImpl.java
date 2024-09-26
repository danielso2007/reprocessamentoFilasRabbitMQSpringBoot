package com.example.spring.producer.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import com.example.spring.producer.api.AmqpApi;
import com.example.spring.producer.dto.MessageQueue;
import com.example.spring.producer.service.AmqpService;

@RestController
public class AmqpApiImpl implements AmqpApi {

    private static final Logger logger = LoggerFactory.getLogger(AmqpApiImpl.class);
    private final AmqpService service;

    public AmqpApiImpl(final AmqpService service) {
        this.service = service;
    }

    @Override
    public void sendToConsumer(final MessageQueue message) {
        logger.info("Enviando mensagem: {}", message);
        service.sendToConsumer(message);
    }

}
