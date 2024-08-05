package com.bhreneer.springdatacrudexample.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponseDTO {

    private String externalId;
    private String title;
    private Integer releaseYear;
    private String rating;
    private String duration;
    private String listedIn;
    private String description;
    private List<CountryResponseDTO> countryList;
    private List<PersonResponseDTO> artistList;
    private List<PersonResponseDTO> directorList;
}
