package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.enums.ShowType;
import com.bhreneer.springdatacrudexample.exception.ValidateException;
import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.model.dto.MovieCSVRecordDTO;
import com.bhreneer.springdatacrudexample.repository.MovieRepository;
import com.bhreneer.springdatacrudexample.service.MovieService;
import com.bhreneer.springdatacrudexample.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    private static final String NULL_CSV_RECORD = "The line is null.";
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public Movie save(Movie movie) {
        return this.movieRepository.save(movie);
    }

    @Override
    public Movie processMovie(MovieCSVRecordDTO csvRecord, List<Country> countries) {
        Movie movie = Movie.builder()
                .tittle(csvRecord.getTitle())
                .countries(countries)
                .dateAdded(csvRecord.getDataAdded())
                .description(csvRecord.getDescription())
                .duration(csvRecord.getDuration())
                .externalId(UUIDUtils.getUUID())
                .listedIn(csvRecord.getListedIn())
                .type(ShowType.valueOfString(csvRecord.getType()))
                .rating(csvRecord.getRating())
                .releaseYear(csvRecord.getReleaseYear())
                .showId(csvRecord.getShowId())
                .build();
        return this.save(movie);

    }

    @Override
    public void validateMovieCSVRecord(CSVRecord csvRecord) throws ValidateException {
        CSVRecord record = Optional.ofNullable(csvRecord).orElseThrow(() -> new ValidateException(NULL_CSV_RECORD));
        if (StringUtils.isEmpty(record.get("title")) || StringUtils.isEmpty(record.get("show_id"))) {
            throw new ValidateException("Invalid record on line ".concat(String.valueOf(record.getRecordNumber())).concat("."));
        }
        Optional<Movie> movieOptional = this.findMovieByShowId(record.get("show_id"));
        if(movieOptional.isPresent()) {
            throw new ValidateException("Movie ".concat(record.get("show_id")).concat(" already exists"));
        }
    }

    public Optional<Movie> findMovieByShowId(String showId) {
        Movie movieFind = Movie.builder().showId(showId).build();
        return movieRepository.findBy(Example.of(movieFind), query -> query.stream().findFirst());
    }
}
