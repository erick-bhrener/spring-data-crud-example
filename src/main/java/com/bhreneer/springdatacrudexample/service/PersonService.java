package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.model.Person;
import com.bhreneer.springdatacrudexample.model.dto.MovieCSVRecordDTO;
import org.apache.commons.csv.CSVRecord;

import java.util.Optional;

public interface PersonService {
    Person save(Person person);

    void processDirector(MovieCSVRecordDTO recordDTO, Movie movie);

    void processCast(MovieCSVRecordDTO recordDTO, Movie movie);

    Optional<Person> findByNameUpper(String nameUpper);
}
