package com.bhreneer.springdatacrudexample.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SpringDataCrudExampleConfig {

    @Value("${cache.expire.time}")
    private Integer cacheExpireTime;

    @Value("${cache.maximum.size}")
    private long cacheMaximumSize;

    @Value("${cache.initial.size}")
    private int cacheInitialSize;

    @Value("${rabbitmq.queue}")
    private String queueName;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routingkey}")
    private String routingkey;

    @Value("${rabbitmq.username}")
    private String username;

    @Value("${rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.host}")
    private String host;

    @Value("${rabbitmq.reply.timeout}")
    private Integer replyTimeout;

    @Value("${rabbitmq.concurrent.consumers}")
    private Integer concurrentConsumers;

    @Value("${rabbitmq.max.concurrent.consumers}")
    private Integer maxConcurrentConsumers;
}
