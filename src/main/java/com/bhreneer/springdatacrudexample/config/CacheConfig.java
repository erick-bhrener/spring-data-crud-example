package com.bhreneer.springdatacrudexample.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Autowired
    SpringDataCrudExampleConfig config;

    @Bean
    @Qualifier("countryCacheManager")
    public CacheManager countryCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(countryCaffeineConfig());
        caffeineCacheManager.setCacheNames(Arrays.asList("CountryCache"));
        return caffeineCacheManager;
    }

    @Bean
    public Caffeine<Object, Object> countryCaffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(config.getCacheExpireTime(), TimeUnit.MINUTES)
                .maximumSize(config.getCountryCacheMaximumSize())
                .initialCapacity(config.getCountryCacheInitialSize())
                .recordStats();
    }
}
