package com.example.spring.consumer.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Service;
import com.example.spring.consumer.dto.MessageQueue;
import com.example.spring.consumer.service.ConsumerService;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private static final String TESTE_RETORNO = "teste";
    private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Override
    public void action(MessageQueue message) {
        if (TESTE_RETORNO.equalsIgnoreCase(message.getText())) {
            logger.error("Erro de retorno de mensagem (Apenas teste)");
            throw new AmqpRejectAndDontRequeueException("erro");
        }
        logger.info("Mensagem recebida: {}", message.getText());
    }
}
