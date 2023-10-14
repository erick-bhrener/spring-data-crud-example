package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.model.Country;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public interface CountryService {

    Country save(Country country);

    List<Country> processCountry(CSVRecord csvRecord);
}
