package com.bhreneer.springdatacrudexample.controller;

import com.bhreneer.springdatacrudexample.builder.CountryTestBuilder;
import com.bhreneer.springdatacrudexample.exception.ValidateException;
import com.bhreneer.springdatacrudexample.model.dto.CountryRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.CountryResponseDTO;
import com.bhreneer.springdatacrudexample.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryService countryService;

    private CountryTestBuilder builder;

    @BeforeEach
    public void setUp() {
        builder = new CountryTestBuilder();
    }

    @Test
    public void findOneCountry_shouldReturnOk() {
        Long countryId = builder.getCountryId();
        CountryResponseDTO countryResponseDTO = builder.buildCountryResponseDTO();

        when(this.countryService.findCountryById(countryId)).thenReturn(countryResponseDTO);
        ResponseEntity<?> result = countryController.getCountry(countryId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(countryResponseDTO, result.getBody());
    }

    @Test
    public void findOneCountry_shouldReturnNotFound() {
        Long countryId = builder.getCountryId();

        when(this.countryService.findCountryById(countryId)).thenThrow( new ValidateException("Country not found", HttpStatus.NOT_FOUND));
        ResponseEntity<?> result = countryController.getCountry(countryId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    public void saveNewCountry_shouldReturnOk_withNewCountry() {
        CountryRequestDTO requestDTO = builder.buildCountryRequestDTO();
        CountryResponseDTO responseDTO = builder.buildCountryResponseDTO();

        when(this.countryService.saveCountry(requestDTO)).thenReturn(responseDTO);
        ResponseEntity<?> result = countryController.saveCountry(requestDTO);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDTO, result.getBody());
    }

    @Test
    public void updateCountry_shouldReturnOk() {
        Long countryId = builder.getCountryId();
        CountryRequestDTO requestDTO = builder.buildCountryRequestDTO();
        CountryResponseDTO responseDTO = builder.buildCountryResponseDTO();

        when(this.countryService.updateCountry(requestDTO, countryId)).thenReturn(responseDTO);
        ResponseEntity<?> result = countryController.updateCountry(requestDTO, countryId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseDTO, result.getBody());
    }

    @Test
    public void updateCountry_shouldReturnNotFound_throwException() {
        Long countryId = builder.getCountryId();
        CountryRequestDTO requestDTO = builder.buildCountryRequestDTO();

        when(this.countryService.updateCountry(requestDTO, countryId)).thenThrow(new ValidateException("Country not found", HttpStatus.NOT_FOUND));
        ResponseEntity<?> result = countryController.updateCountry(requestDTO, countryId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    public void updateCountry_shouldReturnBadRequest_emptyRequestBody() {
        Long countryId = builder.getCountryId();
        CountryRequestDTO requestDTO = builder.buildCountryRequestDTO();

        when(this.countryService.updateCountry(requestDTO, countryId)).thenThrow(new ValidateException("Name cannot be empty", HttpStatus.BAD_REQUEST));
        ResponseEntity<?> result = countryController.updateCountry(requestDTO, countryId);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

}
