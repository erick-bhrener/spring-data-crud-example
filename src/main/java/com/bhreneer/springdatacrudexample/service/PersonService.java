package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.model.Person;
import com.bhreneer.springdatacrudexample.model.dto.*;
import org.apache.commons.csv.CSVRecord;

import java.util.Optional;

public interface PersonService {
    Person save(Person person);

    void processDirector(MovieCSVRecordDTO recordDTO, Movie movie);

    void processCast(MovieCSVRecordDTO recordDTO, Movie movie);

    Optional<Person> findByNameUpper(String nameUpper);

    PersonResponseDTO findPersonById(Long id);

    PersonResponseDTO savePerson(PersonRequestDTO requestDTO);

    PersonFilterResponseDTO findAllPersonByFilter(PersonFilterRequestDTO requestDTO);

    PersonResponseDTO updatePerson(PersonRequestDTO requestDTO, Long id);
}
