package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.model.Person;
import org.apache.commons.csv.CSVRecord;

import java.util.Optional;

public interface PersonService {
    Person save(Person person);

    void processDirector(CSVRecord csvRecord, Movie movie);

    void processCast(CSVRecord csvRecord, Movie movie);

    Optional<Person> findByNameUpper(String nameUpper);
}
