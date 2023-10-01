package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.model.Cast;
import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.model.Person;

public interface CastService {
    Cast save(Cast cast);

    Cast processCastDirector(Person director, Movie movie);

    Cast processCastActor(Person actor, Movie movie);
}
