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
        try{
            InputStream inputStream = file.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            CSVParser csvParser = new CSVParser(bufferedReader,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for(CSVRecord csvRecord : csvRecords) {
                processCSVRecord(csvRecord);
            }

        } catch (UnsupportedEncodingException e) {
            log.error("Error reading csv file.");
            e.printStackTrace();
        } catch (IOException e) {
            log.error("Error reading csv file.");
            e.printStackTrace();
        }
        log.info("End ImportMovieServiceImpl - importMovies: {}", file.getName());
    }

    @Async
    private void processCSVRecord (CSVRecord csvRecord) {
        log.info("Processing movie {}.", csvRecord.get("show_id"));
        List<Country> country = countryService.processCountry(csvRecord);
        Movie movie = movieService.processMovie(csvRecord, country);
        personService.processDirector(csvRecord, movie);
        personService.processCast(csvRecord, movie);
        log.info("Ended processing movie {}.", csvRecord.get("show_id"));
    }

}
