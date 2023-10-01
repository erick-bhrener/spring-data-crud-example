package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.model.Country;
import org.apache.commons.csv.CSVRecord;

public interface CountryService {

    Country save(Country country);

    Country processCountry(CSVRecord csvRecord);
}
