package com.bhreneer.springdatacrudexample.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryRequestDTO {

    @NotNull(message = "Name cannot be null or empty")
    @NotBlank(message = "Name cannot be null or empty")
    @NotEmpty(message = "Name cannot be null or empty")
    private String name;
}
