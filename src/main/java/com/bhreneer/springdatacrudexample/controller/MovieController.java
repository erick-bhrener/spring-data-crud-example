package com.bhreneer.springdatacrudexample.controller;

import com.bhreneer.springdatacrudexample.service.ImportMovieService;
import com.bhreneer.springdatacrudexample.service.MovieService;
import com.bhreneer.springdatacrudexample.utils.CSVUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private ImportMovieService importMovieService;

    @PostMapping(value = "/import", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importFile(@RequestParam("file")MultipartFile csvFile) {
        log.info("Uploading new file.");

        if(CSVUtils.hasCSVFormat(csvFile)) {
            importMovieService.importMovies(csvFile);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new String("Not a CSV file"));

    }

    @GetMapping(value = "/public/status")
    public ResponseEntity<String> status() {
        log.info("Service on.");
        return new ResponseEntity<String>("Service on!",HttpStatus.OK);
    }
}
