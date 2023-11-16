package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.exception.ValidateException;
import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.dto.*;

import java.util.List;

public interface CountryService {

    Country save(Country country);

    List<Country> processCountry(MovieCSVRecordDTO recordDTO);

    CountryFilterResponseDTO findAllCountryByFilter(CountryFilterRequestDTO requestDTO);

    CountryResponseDTO findCountryById(Long id) throws ValidateException;

    CountryResponseDTO saveCountry(CountryRequestDTO requestDTO);

    CountryResponseDTO updateCountry(CountryRequestDTO requestDTO, Long id);
}
