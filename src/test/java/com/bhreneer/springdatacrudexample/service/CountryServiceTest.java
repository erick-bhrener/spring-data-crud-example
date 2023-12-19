package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.builder.CountryBuilder;
import com.bhreneer.springdatacrudexample.builder.CountryTestBuilder;
import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.dto.CountryFilterRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.CountryRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.CountryResponseDTO;
import com.bhreneer.springdatacrudexample.repository.CountryRepository;
import com.bhreneer.springdatacrudexample.service.impl.CountryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

    @InjectMocks
    private CountryServiceImpl countryService;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CacheService countryCacheService;

    @Mock
    private CountryBuilder countryBuilder;

    private CountryTestBuilder builder;

    @BeforeEach
    public void setUp() {
        builder = new CountryTestBuilder();
    }

    @Test
    public void saveCountry_sucess() {
        Country countryToSave = builder.buildCountryEntity();
        given(this.countryRepository.save(any(Country.class))).willReturn(countryToSave);

        Country savedCountry = countryService.save(countryToSave);

        assertEquals(countryToSave, savedCountry);
        verify(countryCacheService, times(1)).put(anyString(), any(Country.class));
    }

    @Test
    public void saveCountry_countryAlreadyExists_returnExistent() {
        CountryRequestDTO requestDTO = builder.buildCountryRequestDTO();
        CountryResponseDTO responseDTO = builder.buildCountryResponseDTO();
        Country entityCountry = builder.buildCountryEntity();

        given(countryService.findCountryByNameUpper(anyString())).willReturn(entityCountry);
        given(countryBuilder.buildFromEntity(any(Country.class))).willReturn(responseDTO);

        CountryResponseDTO resultDTO = countryService.saveCountry(requestDTO);

        assertNotNull(resultDTO);
        verify(countryService, never()).save(any(Country.class));

    }

    @Test
    public void findCountryByName_returnExistingCountryOnCache() {
        String nameUpper = "TEST";
        Country cachedCountry = builder.buildCountryEntity();
        when(countryCacheService.get(nameUpper)).thenReturn(Optional.of(cachedCountry));

        Country result = countryService.findCountryByNameUpper(nameUpper);

        assertEquals(cachedCountry, result);
        verify(countryRepository, never()).findByNameUpper(anyString());

    }

    @Test
    public void findCountryByName_returnExistingCountryOnDatabase() {
        String nameUpper = "TEST";
        Country entityCountry = builder.buildCountryEntity();

        when(countryCacheService.get(nameUpper)).thenReturn(Optional.empty());
        when(countryRepository.findByNameUpper(nameUpper)).thenReturn(Optional.of(entityCountry));

        Country result = countryService.findCountryByNameUpper(nameUpper);

        assertNotNull(result);
        verify(countryRepository, times(1)).findByNameUpper(nameUpper);
    }

    @Test
    public void findCountryByName_returnNull() {
        String nameUpper = "TEST";

        when(countryCacheService.get(nameUpper)).thenReturn(Optional.empty());
        when(countryRepository.findByNameUpper(nameUpper)).thenReturn(Optional.empty());

        Country result = countryService.findCountryByNameUpper(nameUpper);

        assertNull(result);
        verify(countryRepository, times(1)).findByNameUpper(nameUpper);
    }

    @Test
    public void findAllCountriesByFilter_sucess() {
        CountryFilterRequestDTO filterRequestDTO = builder.buildRequestFilterDTO();

    }
}
