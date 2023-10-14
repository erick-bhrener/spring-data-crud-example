package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.Movie;
import org.apache.commons.csv.CSVRecord;

import java.util.List;

public interface MovieService {
    Movie save(Movie movie);

    Movie processMovie(CSVRecord csvRecord, List<Country> countries);
}
