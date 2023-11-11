package com.bhreneer.springdatacrudexample.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SpringDataCrudExampleConfig {

    @Value("${cache.expire.time}")
    private Integer cacheExpireTime;

    @Value("${country-cache.maximum.size}")
    private long countryCacheMaximumSize;

    @Value("${country-cache.initial.size}")
    private int countryCacheInitialSize;
}
