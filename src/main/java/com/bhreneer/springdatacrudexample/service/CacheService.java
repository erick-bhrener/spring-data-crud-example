package com.bhreneer.springdatacrudexample.service;

import org.springframework.lang.Nullable;

import java.util.Optional;

public interface CacheService {
    <T> Optional<T> get(Object key);

    boolean hasKey(Object key);

    void put(Object key, Object objectToCache);
}
