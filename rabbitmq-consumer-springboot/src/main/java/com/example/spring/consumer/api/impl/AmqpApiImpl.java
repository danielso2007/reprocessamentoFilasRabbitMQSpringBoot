package com.example.spring.consumer.api.impl;

import org.springframework.web.bind.annotation.RestController;
import com.example.spring.consumer.api.AmqpApi;
import com.example.spring.consumer.service.RePublishService;

@RestController
public class AmqpApiImpl implements AmqpApi {

    private final RePublishService service;

    public AmqpApiImpl(final RePublishService service) {
        this.service = service;
    }

    @Override
    public void sendToQueue() {
        service.repost();
    }

}
