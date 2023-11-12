package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.model.Cast;
import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.model.Person;
import com.bhreneer.springdatacrudexample.model.dto.MovieCSVRecordDTO;
import com.bhreneer.springdatacrudexample.repository.PersonRepository;
import com.bhreneer.springdatacrudexample.service.CacheService;
import com.bhreneer.springdatacrudexample.service.CastService;
import com.bhreneer.springdatacrudexample.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    private static final String UNDEFINED = "Undefined";

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CastService castService;

    @Autowired
    @Qualifier("PersonCacheServiceImpl")
    private CacheService cacheService;

    @Override
    @Transactional
    public Person save(Person person) {
        Person personSave = this.personRepository.saveAndFlush(person);
        cacheService.put(person.getNameUpper(), personSave);
        return personSave;
    }

    @Override
    public void processDirector(MovieCSVRecordDTO recordDTO, Movie movie) {

        String[] directors = recordDTO.getDirector().trim().split(",");
        for(String director : directors) {
            Person directorPerson = processPerson(director.trim());
            Cast cast = castService.processCastDirector(directorPerson, movie);
        }

    }

    public Person processArtist(String name, Movie movie) {

        Person actor = processPerson(name);

        Cast cast = castService.processCastActor(actor, movie);

        return actor;
    }

    @Override
    public void processCast(MovieCSVRecordDTO recordDTO, Movie movie) {
        if(!StringUtils.hasLength(recordDTO.getCast())) {
            processArtist(null, movie);
        }

        String[] cast = recordDTO.getCast().trim().split(",");
        for (String actor: cast) {
            log.info("Processing actor {}", actor.trim());
            processArtist(actor.trim(), movie);
        }
    }

    private Person processPerson(String name) {
        Person person = null;
        if(!StringUtils.hasLength(name)) {
            person = processUndefined();
        } else {
            Optional<Person> personOptional = this.findByNameUpper(name.toUpperCase());
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
        Optional<Person> person = this.findByNameUpper(UNDEFINED.toUpperCase());
        if(person.isPresent()) {
            return person.get();
        }
        return this.save(Person.builder()
                .name(UNDEFINED)
                .nameUpper(UNDEFINED.toUpperCase())
                .build());
    }

    @Override
    public Optional<Person> findByNameUpper(String nameUpper) {
        Optional<Person> person = cacheService.get(nameUpper);
        if(person.isPresent()) {
            return person;
        }
        return personRepository.findByNameUpper(UNDEFINED.toUpperCase());
    }
}
