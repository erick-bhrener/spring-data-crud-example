package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.dto.MovieCSVRecordDTO;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public interface CountryService {

    Country save(Country country);

    List<Country> processCountry(MovieCSVRecordDTO recordDTO);
}
