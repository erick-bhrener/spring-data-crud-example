package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.exception.ValidateException;
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
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

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
    public String importMovies(MultipartFile file) {
        log.info("ImportMovieServiceImpl - importMovies: {}", file.getName());
        InputStream inputStream = null;
        long totalComputed = 0;
        StringBuilder finalMsg = new StringBuilder();
        try{
            inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(bufferedReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Movie movie = processCSVRecord(csvRecord);
                if(Optional.ofNullable(movie).isPresent()){
                    totalComputed += totalComputed + 1;
                } else {
                    finalMsg.append("Line ").append(csvRecord.getRecordNumber()).append(" was not processed. It already exists.").append(System.lineSeparator());
                }
            }

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
        return finalMsg.append(totalComputed).append(" movies were added.").toString();
    }

    @Async
    public Movie processCSVRecord (CSVRecord csvRecord) {
        log.info("Processing movie {}.", csvRecord.get("show_id"));
        Movie movie = null;
        try {
            movieService.validateMovieCSVRecord(csvRecord);
            List<Country> country = countryService.processCountry(csvRecord);
            movie = movieService.processMovie(csvRecord, country);
            personService.processDirector(csvRecord, movie);
            personService.processCast(csvRecord, movie);
            log.info("Ended processing movie {}.", csvRecord.get("show_id"));
        } catch (ValidateException e) {
            log.error("Error processing movie on line {}", csvRecord.getRecordNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

}
