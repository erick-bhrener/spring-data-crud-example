package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.model.Cast;
import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.model.Person;
import com.bhreneer.springdatacrudexample.repository.PersonRepository;
import com.bhreneer.springdatacrudexample.service.CastService;
import com.bhreneer.springdatacrudexample.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private static final String UNDEFINED = "Undefined";
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CastService castService;

    @Override
    public Person save(Person person) {
        return this.personRepository.save(person);
    }

    @Override
    public Person processDirector(CSVRecord csvRecord, Movie movie) {

        Person director = processPerson(csvRecord.get("director"));

        Cast cast = castService.processCastDirector(director, movie);

        return director;
    }

    public Person processArtist(String name, Movie movie) {

        Person actor = processPerson(name);

        Cast cast = castService.processCastActor(actor, movie);

        return actor;
    }

    @Override
    public void processCast(CSVRecord csvRecord, Movie movie) {
        if(!csvRecord.isSet("cast")) {
            processArtist(null, movie);
        }

        String[] cast = csvRecord.get("cast").split(",");
        for (String actor: cast) {
            processArtist(actor, movie);
        }
    }

    private Person processPerson(String name) {
        Person person = null;
        if(StringUtils.hasLength(name)) {
            person = processUndefined();
        } else {
            Optional<Person> personOptional = personRepository.findByNameUpper(name.toUpperCase());
            if(personOptional.isPresent()) {
                person = personOptional.get();
            } else {
                person = this.save(Person.builder()
                        .name(name)
                        .nameUpper(name.toUpperCase())
                        .build());
            }
        }
        return person;
    }

    private Person processUndefined() {
        Optional<Person> person = personRepository.findByNameUpper(UNDEFINED.toUpperCase());
        if(person.isPresent()) {
            return person.get();
        }
        return this.save(Person.builder()
                .name(UNDEFINED)
                .nameUpper(UNDEFINED.toUpperCase())
                .build());
    }
}
