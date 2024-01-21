package com.bhreneer.springdatacrudexample.controller;

import com.bhreneer.springdatacrudexample.exception.ValidateException;
import com.bhreneer.springdatacrudexample.model.dto.CountryFilterRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.CountryRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.PersonFilterRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.PersonRequestDTO;
import com.bhreneer.springdatacrudexample.service.PersonService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@OpenAPIDefinition(info=@Info(
        title = "Person Controller"
))
@Slf4j
@RestController
@RequestMapping("/person")
@Validated
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPerson(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personService.findPersonById(id));
        } catch (ValidateException ve) {
            return ResponseEntity.status(ve.getHttpStatus()).body(ve.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePerson(@Valid @RequestBody PersonRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.savePerson(requestDTO));
    }

    @PostMapping
    public ResponseEntity<?> getAllPersonByFilter(@RequestBody PersonFilterRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findAllPersonByFilter(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerson(@Valid @RequestBody PersonRequestDTO requestDTO, @RequestParam("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(personService.updatePerson(requestDTO, id));
        } catch (ValidateException ve) {
            return ResponseEntity.status(ve.getHttpStatus()).body(ve.getMessage());
        }
    }
}
