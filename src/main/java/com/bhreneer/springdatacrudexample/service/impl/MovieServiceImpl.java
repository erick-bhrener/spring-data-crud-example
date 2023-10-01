package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.enums.ShowType;
import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.repository.MovieRepository;
import com.bhreneer.springdatacrudexample.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie save(Movie movie) {
        return this.movieRepository.save(movie);
    }

    @Override
    public Movie processMovie(CSVRecord csvRecord, Country country) {
        //show_id,type,title,director,cast,country,date_added,release_year,rating,duration,listed_in,description
        if(csvRecord.isSet("show_id")) {
            Movie movie = Movie.builder()
                    .country(country)
                    .dateAdded(csvRecord.get("date_added"))
                    .description(csvRecord.get("description"))
                    .duration(csvRecord.get("duration"))
                    .externalId("")
                    .listedIn(csvRecord.get("listed_in"))
                    .type(ShowType.valueOf(csvRecord.get("type")).name())
                    .rating(csvRecord.get("rating"))
                    .releaseYar(csvRecord.get("rating"))
                    .showId(csvRecord.get("show_id"))
                    .build();
            return this.save(movie);
        }

        return null;
    }
}
