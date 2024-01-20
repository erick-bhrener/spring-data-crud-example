package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.builder.CountryBuilder;
import com.bhreneer.springdatacrudexample.exception.ValidateException;
import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.dto.*;
import com.bhreneer.springdatacrudexample.repository.CountryRepository;
import com.bhreneer.springdatacrudexample.service.CacheService;
import com.bhreneer.springdatacrudexample.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private CountryBuilder builder;

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Country save(Country country) {
        Country countrySave = countryRepository.save(country);
        countryCacheService.put(country.getNameUpper(), countrySave);
        return countrySave;
    }

    @Override
    public List<Country> processCountry(MovieCSVRecordDTO recordDTO) {
        if(!StringUtils.hasLength(recordDTO.getCountry()) || !StringUtils.hasLength(recordDTO.getCountry().trim())) {
            return Arrays.asList(processUndefined());
        }

        List<Country> listCountry = new ArrayList<>();
        String[] countries = recordDTO.getCountry().trim().split(",");

        for (String c : countries) {
            listCountry.add(processCountry(c.trim()));
        }

        return listCountry;

    }

    private Country processCountry(String country) {
        Optional<Country> countryFind = findCountryByNameUpper(country.toUpperCase());
        if(countryFind.isPresent()) {
            return countryFind.get();
        }

        Country countrySave = Country.builder()
                .name(country)
                .nameUpper(country.toUpperCase())
                .build();
        return this.save(countrySave);
    }

    private Country processUndefined() {
        Optional<Country> country = findCountryByNameUpper(UNDEFINED.toUpperCase());
        if(country.isPresent()) {
            return country.get();
        }
        return this.save(Country.builder()
                .name(UNDEFINED)
                .nameUpper(UNDEFINED.toUpperCase())
                .build());
    }

    public Optional<Country> findCountryByNameUpper(String nameUpper) {
        Optional<Country> countryCache = countryCacheService.get(nameUpper);
        if(countryCache.isPresent()) {
            return countryCache;
        }
        Optional<Country> country = countryRepository.findByNameUpper(nameUpper);
        return country;
    }

    @Override
    public CountryFilterResponseDTO findAllCountryByFilter(CountryFilterRequestDTO requestDTO) {
        Pageable pageable = builder.buildFromRequestDTO(requestDTO);

        Page<Country> countryPage = null;

        if (!StringUtils.hasLength(requestDTO.getName())) {
            countryPage = countryRepository.findAll(pageable);
        } else {
            countryPage = countryRepository.findAllCountryByFilter(requestDTO.getName().toUpperCase().trim(), pageable);
        }

        List<CountryResponseDTO> listResponse = countryPage.getContent().stream()
                .map(country -> builder.buildFromEntity(country)).collect(Collectors.toList());

        return CountryFilterResponseDTO.builder()
                .size(countryPage.getSize())
                .page(countryPage.getNumber())
                .listCountry(listResponse)
                .build();
    }

    @Override
    public CountryResponseDTO findCountryById(Long id) throws ValidateException {
        Country country = countryRepository.findById(id).orElseThrow(() ->
            new ValidateException("Country not found", HttpStatus.NOT_FOUND)
        );

        return builder.buildFromEntity(country);
    }

    @Override
    public CountryResponseDTO saveCountry(CountryRequestDTO requestDTO) {
        String name = requestDTO.getName().trim();
        Country country = this.findCountryByNameUpper(name.toUpperCase()).orElse(null);
        if(ObjectUtils.isEmpty(country)) {
            country = this.save(Country.builder()
                            .name(name)
                            .nameUpper(name.toUpperCase())
                    .build());
        }

        return builder.buildFromEntity(country);
    }

    @Override
    public CountryResponseDTO updateCountry(CountryRequestDTO requestDTO, Long id) {
        if(ObjectUtils.isEmpty(requestDTO) || StringUtils.hasLength(requestDTO.getName())) {
            throw new ValidateException("Name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        String name = requestDTO.getName().trim();
        Country country = countryRepository.findById(id).orElseThrow(() ->
                new ValidateException("Country not found", HttpStatus.NOT_FOUND)
        );
        country.setName(name);
        country.setNameUpper(name.toUpperCase());
        this.countryRepository.save(country);
        return builder.buildFromEntity(country);
    }
}
