package com.example.spring.consumer.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerRabbitConfiguration {

    private final String exchange;
    private final String routingKey;
    private final String deadLetter;
    private final String parkingLot;

    public ProducerRabbitConfiguration(
            @Value("${spring.rabbitmq.request.exchenge.producer}") String exchange,
            @Value("${spring.rabbitmq.request.routing-key.producer}") String routingKey,
            @Value("${spring.rabbitmq.request.dead-letter.producer}") String deadLetter,
            @Value("${spring.rabbitmq.request.parking-lot.producer}") String parkingLot) {
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.deadLetter = deadLetter;
        this.parkingLot = parkingLot;
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(routingKey).deadLetterExchange(exchange)
                .deadLetterRoutingKey(deadLetter).build();
    }

    @Bean
    Queue deadLetter() {
        return QueueBuilder.durable(deadLetter).deadLetterExchange(exchange)
                .deadLetterRoutingKey(routingKey).build();
    }

    @Bean
    Queue parkingLot() {
        return new Queue(parkingLot);
    }

    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding bindingQueue() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(routingKey);
    }

    @Bean
    Binding bindingDeadLetter() {
        return BindingBuilder.bind(deadLetter()).to(directExchange()).with(deadLetter);
    }

    @Bean
    Binding bindingParkingLot() {
        return BindingBuilder.bind(parkingLot()).to(directExchange()).with(parkingLot);
    }

}
