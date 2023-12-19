package com.bhreneer.springdatacrudexample.builder;

import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.dto.CountryFilterRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.CountryRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.CountryResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class CountryTestBuilder {

    private static final Long countryId = 1L;

    public CountryRequestDTO buildCountryRequestDTO () {
        return CountryRequestDTO.builder()
                .name("test")
                .build();
    }

    public CountryResponseDTO buildCountryResponseDTO() {
        return CountryResponseDTO.builder()
                .name("test")
                .id(countryId)
                .build();
    }

    public Long getCountryId() {
        return countryId;
    }


    public Country buildCountryEntity() {
        return Country.builder()
                .nameUpper("TEST")
                .name("Test")
                .id(getCountryId())
                .build();
    }

    public Country buildCountryEntity_2() {
        return Country.builder()
                .nameUpper("TEST2")
                .name("Test2")
                .id(2L)
                .build();
    }

    public CountryFilterRequestDTO buildRequestFilterDTO() {
        return CountryFilterRequestDTO.builder()
                .size(10)
                .page(0)
                .build();
    }
}
