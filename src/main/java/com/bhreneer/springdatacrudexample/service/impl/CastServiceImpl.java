package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.enums.RoleType;
import com.bhreneer.springdatacrudexample.model.Cast;
import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.model.Person;
import com.bhreneer.springdatacrudexample.repository.CastRepository;
import com.bhreneer.springdatacrudexample.service.CastService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CastServiceImpl implements CastService {

    @Autowired
    private CastRepository castRepository;

    @Override
    public Cast save(Cast cast) {
        return this.castRepository.save(cast);
    }

    @Override
    public Cast processCastDirector(Person director, Movie movie) {
        return this.save(Cast.builder()
                .person(director)
                .movie(movie)
                .role(RoleType.DIRECTOR.name())
                .build());
    }

    @Override
    public Cast processCastActor(Person actor, Movie movie) {
        return this.save(Cast.builder()
                .person(actor)
                .movie(movie)
                .role(RoleType.ARTIST.name())
                .build());
    }
}
