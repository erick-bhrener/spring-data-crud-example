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
}
