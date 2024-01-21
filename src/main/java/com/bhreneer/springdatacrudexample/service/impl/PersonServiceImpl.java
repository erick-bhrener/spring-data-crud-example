package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.builder.PersonBuilder;
import com.bhreneer.springdatacrudexample.exception.ValidateException;
import com.bhreneer.springdatacrudexample.model.Cast;
import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.model.Person;
import com.bhreneer.springdatacrudexample.model.dto.*;
import com.bhreneer.springdatacrudexample.repository.PersonRepository;
import com.bhreneer.springdatacrudexample.service.CacheService;
import com.bhreneer.springdatacrudexample.service.CastService;
import com.bhreneer.springdatacrudexample.service.PersonService;
import com.bhreneer.springdatacrudexample.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    private PersonBuilder personBuilder;

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
            log.info("Director saved {}", directorPerson.toString());
            Cast cast = castService.processCastDirector(directorPerson, movie);
        }

    }

    public Person processArtist(String name, Movie movie) {

        Person actor = processPerson(name);
        log.info("Actor saved {}", actor.toString());
        Cast cast = castService.processCastActor(actor, movie);

        return actor;
    }

    @Override
    public void processCast(MovieCSVRecordDTO recordDTO, Movie movie) {
        if(!StringUtils.isNotEmpty(recordDTO.getCast())) {
            processArtist(null, movie);
        }

        String[] cast = recordDTO.getCast().trim().split(",");
        for (String actor: cast) {
            log.info("Processing actor {}", actor.trim());
            processArtist(actor.trim(), movie);
        }
    }

    private Person processPerson(String name) {
        log.info("Processing person {} - is null? {}", name, !com.bhreneer.springdatacrudexample.utils.StringUtils.isNotEmpty(name));
        Person person = null;
        if(!StringUtils.isNotEmpty(name)) {
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
        return personRepository.findByNameUpper(nameUpper);
    }

    @Override
    public PersonResponseDTO findPersonById(Long id) {
        Person personResult = this.personRepository.findById(id).orElseThrow(() ->
                new ValidateException("Person not found", HttpStatus.NOT_FOUND)
        );
        return personBuilder.buildFromEntity(personResult);
    }

    @Override
    public PersonResponseDTO savePerson(PersonRequestDTO requestDTO) {
        String name = requestDTO.getName().trim();
        Person personResult = this.findByNameUpper(name)
                .orElse(
                    this.save(personBuilder.buildPerson(name))
        );
        return personBuilder.buildFromEntity(personResult);
    }

    @Override
    public PersonFilterResponseDTO findAllPersonByFilter(PersonFilterRequestDTO requestDTO) {
        Pageable pageable = personBuilder.buildFromRequestDTO(requestDTO);

        Page<Person> personPage = null;
        if(StringUtils.isNotEmpty(requestDTO.getName())) {
            personPage = this.personRepository.findAllPersonByFilter(requestDTO.getName(), pageable);
        } else {
            personPage = this.personRepository.findAll(pageable);
        }

        List<PersonResponseDTO> persons = personPage.getContent().stream().map(person -> personBuilder.buildFromEntity(person)).collect(Collectors.toList());

        return personBuilder.buildPersonFilterResponseDTO(persons, personPage);
    }

    @Override
    public PersonResponseDTO updatePerson(PersonRequestDTO requestDTO, Long id) {
        if(ObjectUtils.isEmpty(requestDTO) || StringUtils.isNotEmpty(requestDTO.getName())) {
            throw new ValidateException("Name cannot be empty", HttpStatus.BAD_REQUEST);
        }
        Person personResult = this.personRepository.findById(id).orElseThrow(() ->
            new ValidateException("Person not found", HttpStatus.NOT_FOUND)
        );
        String name = requestDTO.getName().trim();
        personResult.setName(name);
        personResult.setNameUpper(name.toUpperCase());
        this.save(personResult);
        return personBuilder.buildFromEntity(personResult);
    }
}
