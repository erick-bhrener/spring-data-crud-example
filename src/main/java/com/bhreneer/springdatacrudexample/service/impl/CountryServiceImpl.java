package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.repository.CountryRepository;
import com.bhreneer.springdatacrudexample.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    private static final String UNDEFINED = "Undefined";
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
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
        Optional<Country> countryOptional = countryRepository.findByNameUpper(country.toUpperCase());
        if(countryOptional.isPresent()) {
            return countryOptional.get();
        }

        Country countrySave = Country.builder()
                .name(country)
                .nameUpper(country.toUpperCase())
                .build();
        return this.save(countrySave);
    }

    private Country processUndefined() {
        Optional<Country> country = countryRepository.findByNameUpper(UNDEFINED.toUpperCase());
        if(country.isPresent()) {
            return country.get();
        }
        return this.save(Country.builder()
                .name(UNDEFINED)
                .nameUpper(UNDEFINED.toUpperCase())
                .build());
    }
}
