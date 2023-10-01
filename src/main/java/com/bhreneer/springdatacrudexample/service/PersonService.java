package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.model.Person;
import org.apache.commons.csv.CSVRecord;

public interface PersonService {
    Person save(Person person);

    Person processDirector(CSVRecord csvRecord, Movie movie);

    void processCast(CSVRecord csvRecord, Movie movie);
}
