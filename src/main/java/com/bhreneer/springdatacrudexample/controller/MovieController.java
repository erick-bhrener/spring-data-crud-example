package com.bhreneer.springdatacrudexample.controller;

import com.bhreneer.springdatacrudexample.service.ImportMovieService;
import com.bhreneer.springdatacrudexample.service.MovieService;
import com.bhreneer.springdatacrudexample.utils.CSVUtils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@OpenAPIDefinition(info=@Info(
        title = "Movie Controller"
))
@Slf4j
@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private ImportMovieService importMovieService;

    @Operation
    @PostMapping(value = "/import",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importFile(@RequestPart("file") MultipartFile csvFile) {
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
