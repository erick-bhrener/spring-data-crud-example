package com.bhreneer.springdatacrudexample.controller;

import com.bhreneer.springdatacrudexample.exception.ValidateException;
import com.bhreneer.springdatacrudexample.model.dto.CountryFilterRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.CountryRequestDTO;
import com.bhreneer.springdatacrudexample.service.MovieService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@OpenAPIDefinition(info=@Info(
        title = "Movie Controller"
))
@Slf4j
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovie(@RequestParam("id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.findMovieById(id));
        } catch (ValidateException ve) {
            return ResponseEntity.status(ve.getHttpStatus()).body(ve.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveCountry(@Valid @RequestBody CountryRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.saveCountry(requestDTO));
    }

    @PostMapping
    public ResponseEntity<?> getAllCountryByFilter(@RequestBody CountryFilterRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findAllCountryByFilter(requestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCountry(@Valid @RequestBody CountryRequestDTO requestDTO, @RequestParam("id") Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(movieService.updateCountry(requestDTO, id));
        } catch (ValidateException ve) {
            return ResponseEntity.status(ve.getHttpStatus()).body(ve.getMessage());
        }
    }

    @GetMapping(value = "/public/status")
    public ResponseEntity<String> status() {
        log.info("Service on.");
        return new ResponseEntity<String>("Service on!",HttpStatus.OK);
    }
}
