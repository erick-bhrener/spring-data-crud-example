package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.repository.CountryRepository;
import com.bhreneer.springdatacrudexample.service.CacheService;
import com.bhreneer.springdatacrudexample.service.CountryService;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
@CacheConfig(cacheNames = {"CountryCache"})
public class CountryServiceImpl implements CountryService {

    private static final String UNDEFINED = "Undefined";
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    @Qualifier("CountryCacheServiceImpl")
    private CacheService countryCacheService;

    @Override
    public Country save(Country country) {

        Country countrySave = countryRepository.save(country);
        countryCacheService.put(country.getNameUpper(), countrySave);
        return countrySave;
    }

    @Override
    public List<Country> processCountry(CSVRecord csvRecord) {
        if(!csvRecord.isSet("country") || !StringUtils.hasLength(csvRecord.get("country").trim())) {
            return Arrays.asList(processUndefined());
        }

        List<Country> listCountry = new ArrayList<>();
        String[] countries = csvRecord.get("country").trim().split(",");

        for (String c : countries) {
            listCountry.add(processCountry(c.trim()));
        }

        return listCountry;

    }

    private Country processCountry(String country) {
        Country countryFind = findCountryByNameUpper(country.toUpperCase());
        if(!ObjectUtils.isEmpty(countryFind)) {
            return countryFind;
        }

        Country countrySave = Country.builder()
                .name(country)
                .nameUpper(country.toUpperCase())
                .build();
        return this.save(countrySave);
    }

    private Country processUndefined() {
        Country country = findCountryByNameUpper(UNDEFINED.toUpperCase());
        if(!ObjectUtils.isEmpty(country)) {
            return country;
        }
        return this.save(Country.builder()
                .name(UNDEFINED)
                .nameUpper(UNDEFINED.toUpperCase())
                .build());
    }

    public Country findCountryByNameUpper(String nameUpper) {
        log.info("Searching country: {}", nameUpper);
        Optional<Country> countryCache = countryCacheService.get(nameUpper);
        if(countryCache.isPresent()) {
            log.info("Returning cached country: {}", countryCache.get().toString());
            return countryCache.get();
        }
        Optional<Country> country = countryRepository.findByNameUpper(nameUpper);
        return country.orElse(null);
    }
}
