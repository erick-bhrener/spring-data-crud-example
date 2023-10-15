package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.service.CountryService;
import com.bhreneer.springdatacrudexample.service.ImportMovieService;
import com.bhreneer.springdatacrudexample.service.MovieService;
import com.bhreneer.springdatacrudexample.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ImportMovieServiceImpl implements ImportMovieService {

    static String[] HEADERs = { "show_id","type","title","director","cast","country","date_added","release_year","rating","duration","listed_in","description" };

    @Autowired
    private CountryService countryService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private PersonService personService;

    @Override
    public void importMovies(MultipartFile file) {
        log.info("ImportMovieServiceImpl - importMovies: {}", file.getName());
        InputStream inputStream = null;
        long totalComputed = 0;
        try{
            inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            CSVParser csvParser = new CSVParser(bufferedReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<CSVRecord> csvRecords = csvParser.getRecords();
            totalComputed = csvRecords
                    .parallelStream()
                    .map(csvRecord -> processCSVRecord(csvRecord))
                    .count();

        } catch (UnsupportedEncodingException e) {
            log.error("Error reading csv file.");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("Error reading csv file.");
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        log.info("End ImportMovieServiceImpl - importMovies: {} - Total Movies: {}", file.getName(), totalComputed);
    }

    @Async
    public Movie processCSVRecord (CSVRecord csvRecord) {
        log.info("Processing movie {}.", csvRecord.get("show_id"));
        Movie movie = null;
        try {
            List<Country> country = countryService.processCountry(csvRecord);
            movie = movieService.processMovie(csvRecord, country);
            personService.processDirector(csvRecord, movie);
            personService.processCast(csvRecord, movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Ended processing movie {}.", csvRecord.get("show_id"));
        return movie;
    }

}
