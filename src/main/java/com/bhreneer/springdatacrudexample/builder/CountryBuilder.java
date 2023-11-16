package com.bhreneer.springdatacrudexample.builder;

import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.dto.CountryFilterRequestDTO;
import com.bhreneer.springdatacrudexample.model.dto.CountryResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class CountryBuilder {

    public Pageable buildFromRequestDTO (CountryFilterRequestDTO requestDTO) {
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

    public CountryResponseDTO buildFromEntity(Country country) {
        return CountryResponseDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .build();
    }
}
