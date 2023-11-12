package com.bhreneer.springdatacrudexample.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieCSVRecordDTO {

    private String showId;
    private String type;
    private String title;
    private String director;
    private String cast;
    private String country;
    private String dataAdded;
    private Integer releaseYear;
    private String rating;
    private String duration;
    private String listedIn;
    private String description;
}
