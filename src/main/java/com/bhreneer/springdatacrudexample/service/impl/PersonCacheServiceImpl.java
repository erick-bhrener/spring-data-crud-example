package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("PersonCacheServiceImpl")
public class PersonCacheServiceImpl implements CacheService {

    @Autowired
    @Qualifier("personCacheManager")
    private CacheManager cacheManager;

    @Override
    public <Person> Optional<Person> get(Object key) {
        Cache cache = cacheManager.getCache("PersonCache");
        Cache.ValueWrapper wrapper = cache.get(key);
        if(wrapper != null) {
            return Optional.of((Person) wrapper.get());
        }
        return Optional.empty();
    }

    @Override
    public boolean hasKey(Object key) {
        return false;
    }

    @Override
    public void put(Object key, Object objectToCache) {
        Cache cache = cacheManager.getCache("PersonCache");
        cache.put(key, objectToCache);
    }
}
