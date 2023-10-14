package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.enums.ShowType;
import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.repository.MovieRepository;
import com.bhreneer.springdatacrudexample.service.MovieService;
import com.bhreneer.springdatacrudexample.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

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
    public Movie processMovie(CSVRecord csvRecord, List<Country> countries) {
        //show_id,type,title,director,cast,country,date_added,release_year,rating,duration,listed_in,description
        if(csvRecord.isSet("show_id")) {
            Movie movie = Movie.builder()
                    .tittle(csvRecord.get("title"))
                    .countries(countries)
                    .dateAdded(csvRecord.get("date_added"))
                    .description(csvRecord.get("description"))
                    .duration(csvRecord.get("duration"))
                    .externalId(UUIDUtils.getUUID())
                    .listedIn(csvRecord.get("listed_in"))
                    .type(ShowType.valueOfString(csvRecord.get("type")))
                    .rating(csvRecord.get("rating"))
                    .releaseYear(ObjectUtils.isEmpty(csvRecord.get("release_year")) ? null : Integer.valueOf(csvRecord.get("release_year")))
                    .showId(csvRecord.get("show_id"))
                    .build();
            return this.save(movie);
        }

        return null;
    }
}
