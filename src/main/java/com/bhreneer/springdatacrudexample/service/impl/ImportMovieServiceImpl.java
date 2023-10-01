package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.service.ImportMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImportMovieServiceImpl implements ImportMovieService {

    @Override
    public void importMovies() {
        log.info("ImportMovieServiceImpl - importMovies: Init.");
    }
}
