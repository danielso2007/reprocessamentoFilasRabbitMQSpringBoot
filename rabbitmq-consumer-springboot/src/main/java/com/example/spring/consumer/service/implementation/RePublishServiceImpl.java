package com.example.spring.consumer.service.implementation;

import org.springframework.stereotype.Service;
import com.example.spring.consumer.amqp.AmqpRePublish;
import com.example.spring.consumer.service.RePublishService;

@Service
public class RePublishServiceImpl implements RePublishService {

    private final AmqpRePublish rePublish;

    public RePublishServiceImpl(AmqpRePublish rePublish) {
        this.rePublish = rePublish;
    }

    @Override
    public void repost() {
        rePublish.rePublish();
    }
}
