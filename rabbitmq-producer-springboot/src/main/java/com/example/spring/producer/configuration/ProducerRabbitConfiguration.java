package com.example.spring.producer.configuration;

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

    private final String queue;
    private final String exchange;
    private final String deadLetter;
    private final String parkingLot;

    public ProducerRabbitConfiguration(
            @Value("${spring.rabbitmq.request.routing-key.producer}") String queue,
            @Value("${spring.rabbitmq.request.exchenge.producer}") String exchange,
            @Value("${spring.rabbitmq.request.dead-letter.producer}") String deadLetter,
            @Value("${spring.rabbitmq.request.parking-lot.producer}") String parkingLot) {
        this.queue = queue;
        this.exchange = exchange;
        this.deadLetter = deadLetter;
        this.parkingLot = parkingLot;
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue deadLetter() {
        return QueueBuilder
            .durable(deadLetter)
            .deadLetterExchange(exchange)
            .deadLetterRoutingKey(queue)
            .build();
    }

    @Bean
    Queue queue() {
        return QueueBuilder
            .durable(queue)
            .deadLetterExchange(exchange)
            .deadLetterRoutingKey(deadLetter)
            .build();
    }

    @Bean
    Queue parkingLot() {
        return new Queue(parkingLot);
    }

    @Bean
    public Binding bindingQueue() {
        return BindingBuilder
            .bind(queue())
            .to(exchange())
            .with(queue);
    }

    @Bean
    public Binding bindingDeadLetter() {
        return BindingBuilder
            .bind(deadLetter())
            .to(exchange())
            .with(deadLetter);
    }

    @Bean
    public Binding bindingParkingLot() {
        return BindingBuilder
            .bind(parkingLot())
            .to(exchange())
            .with(parkingLot);
    }

}
