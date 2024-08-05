package com.bhreneer.springdatacrudexample.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieFilterRequestDTO {

    private Integer page;
    private Integer size;
    private String title;
    private String showId;
    private String country;
    private String artist;
    private String director;
}
