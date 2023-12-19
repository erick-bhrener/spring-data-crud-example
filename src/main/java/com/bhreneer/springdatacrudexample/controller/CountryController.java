package com.bhreneer.springdatacrudexample.controller;

import com.bhreneer.springdatacrudexample.exception.ValidateException;
import com.bhreneer.springdatacrudexample.model.dto.CountryFilterRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.CountryRequestDTO;
import com.bhreneer.springdatacrudexample.service.CountryService;
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
        title = "Country Controller"
))
@Slf4j
@RestController
@RequestMapping("/country")
@Validated
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCountry(@RequestParam("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(countryService.findCountryById(id));
        } catch (ValidateException ve) {
            return ResponseEntity.status(ve.getHttpStatus()).body(ve.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCountry(@Valid @RequestBody CountryRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(countryService.saveCountry(requestDTO));
    }

    @PostMapping
    public ResponseEntity<?> getAllCountryByFilter(@RequestBody CountryFilterRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(countryService.findAllCountryByFilter(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCountry(@Valid @RequestBody CountryRequestDTO requestDTO, @RequestParam("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(countryService.updateCountry(requestDTO, id));
        } catch (ValidateException ve) {
            return ResponseEntity.status(ve.getHttpStatus()).body(ve.getMessage());
        }
    }
}
