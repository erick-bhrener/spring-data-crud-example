package com.bhreneer.springdatacrudexample.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Autowired
    SpringDataCrudExampleConfig config;

    @Bean
    @Primary
    @Qualifier("countryCacheManager")
    public CacheManager countryCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(CaffeineConfig());
        caffeineCacheManager.setCacheNames(Arrays.asList("CountryCache"));
        return caffeineCacheManager;
    }

    @Bean
    @Qualifier("personCacheManager")
    public CacheManager personCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(CaffeineConfig());
        caffeineCacheManager.setCacheNames(Arrays.asList("PersonCache"));
        return caffeineCacheManager;
    }

    @Bean
    public Caffeine<Object, Object> CaffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(config.getCacheExpireTime(), TimeUnit.MINUTES)
                .maximumSize(config.getCacheMaximumSize())
                .initialCapacity(config.getCacheInitialSize())
                .recordStats();
    }

}
