package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.Person;
import com.bhreneer.springdatacrudexample.repository.CountryRepository;
import com.bhreneer.springdatacrudexample.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Country processCountry(CSVRecord csvRecord) {

        if(!csvRecord.isSet("country")) {
            return processUndefined();
        }

        Optional<Country> country = countryRepository.findByNameUpper(csvRecord.get("country").toUpperCase());
        if(country.isPresent()) {
            return country.get();
        }

        Country countrySave = Country.builder()
                .name(csvRecord.get("country"))
                .nameUpper(csvRecord.get("country").toUpperCase())
                .build();
        return save(countrySave);
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
