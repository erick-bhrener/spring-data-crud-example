package com.bhreneer.springdatacrudexample.service.impl;

import com.bhreneer.springdatacrudexample.builder.MovieCSVRecordBuilder;
import com.bhreneer.springdatacrudexample.exception.ValidateException;
import com.bhreneer.springdatacrudexample.model.Country;
import com.bhreneer.springdatacrudexample.model.Movie;
import com.bhreneer.springdatacrudexample.model.dto.MovieCSVRecordDTO;
import com.bhreneer.springdatacrudexample.publisher.MoviePublisher;
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
import java.util.concurrent.atomic.AtomicInteger;

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

    @Autowired
    private MoviePublisher moviePublisher;

    @Autowired
    private MovieCSVRecordBuilder builder;

    @Override
    public String importMovies(MultipartFile file) {
        log.info("ImportMovieServiceImpl - importMovies: {}", file.getName());
        long start = System.currentTimeMillis();
        InputStream inputStream = null;
        AtomicInteger totalComputed = new AtomicInteger(0);
        StringBuilder finalMsg = new StringBuilder();
        try{
            inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            CSVParser csvParser = new CSVParser(bufferedReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            List<CSVRecord> csvRecords = csvParser.getRecords();

            AtomicInteger finalTotalComputed = totalComputed;

            csvRecords.parallelStream().forEach(csvRecord -> {
                MovieCSVRecordDTO recordDTO = processCSVRecord(csvRecord);
                if(Optional.ofNullable(recordDTO).isPresent()){
                    finalTotalComputed.getAndIncrement();
                } else {
                    finalMsg.append("Line ").append(csvRecord.getRecordNumber()).append(" was not processed. It already exists.").append(System.lineSeparator());
                }
            });
            totalComputed = finalTotalComputed;
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
        long end = System.currentTimeMillis();
        log.info("End ImportMovieServiceImpl - importMovies: {} - Total Movies: {} - end-start {} ms", file.getName(), totalComputed, end-start);
        return finalMsg.append(totalComputed).append(" movies were added. Time: ").append(end-start).append(" ms").toString();
    }

    @Async
    public MovieCSVRecordDTO processCSVRecord (CSVRecord csvRecord) {
        log.info("Processing movie {}.", csvRecord.get("show_id"));
        MovieCSVRecordDTO recordDTO = null;
        try {
            movieService.validateMovieCSVRecord(csvRecord);
            recordDTO = builder.buildDTO(csvRecord);
            moviePublisher.processMovieCSVRecord(recordDTO);
            log.info("Ended processing movie {}.", csvRecord.get("show_id"));
        } catch (ValidateException e) {
            log.error("Error processing movie on line {}", csvRecord.getRecordNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recordDTO;
    }

    @Override
    public void processCSVRecordFromQueue(MovieCSVRecordDTO recordDTO) {
        log.info("Processing movie {}.", recordDTO.getShowId());
        Movie movie = null;
        try {
            List<Country> country = countryService.processCountry(recordDTO);
            movie = movieService.processMovie(recordDTO, country);
            personService.processDirector(recordDTO, movie);
            personService.processCast(recordDTO, movie);
            log.info("Ended processing movie {}.", recordDTO.getShowId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
