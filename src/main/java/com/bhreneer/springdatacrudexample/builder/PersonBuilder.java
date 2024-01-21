package com.bhreneer.springdatacrudexample.builder;

import com.bhreneer.springdatacrudexample.model.Person;
import com.bhreneer.springdatacrudexample.model.dto.CountryFilterRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.PersonFilterRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.PersonFilterResponseDTO;
import com.bhreneer.springdatacrudexample.model.dto.PersonResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class PersonBuilder {

    public Pageable buildFromRequestDTO (PersonFilterRequestDTO requestDTO) {
        Integer page = 0;
        Integer size = 25;
        if(!ObjectUtils.isEmpty(requestDTO.getSize()) || requestDTO.getSize() < 1) {
            size = requestDTO.getSize();
        }
        if(!ObjectUtils.isEmpty(requestDTO.getPage())) {
            page = requestDTO.getPage();
        }

        return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
    }

    public PersonResponseDTO buildFromEntity(Person person) {
        return PersonResponseDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .build();
    }

    public Person buildPerson(String name) {
        return Person.builder()
                .nameUpper(name.toUpperCase())
                .name(name)
                .build();
    }

    public PersonFilterResponseDTO buildPersonFilterResponseDTO(List<PersonResponseDTO> persons, Page<Person> personPage) {
        return PersonFilterResponseDTO.builder()
                .page(personPage.getNumber())
                .size(persons.size())
                .listCountry(persons)
                .build();
    }
}
