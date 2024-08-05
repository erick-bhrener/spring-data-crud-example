package com.bhreneer.springdatacrudexample.controller;

import com.bhreneer.springdatacrudexample.service.ImportMovieService;
import com.bhreneer.springdatacrudexample.utils.CSVUtils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@OpenAPIDefinition(info=@Info(
        title = "CSV Importer Controller"
))
@Slf4j
@RestController
public class FileImportController {

    @Autowired
    private ImportMovieService importMovieService;

    @Operation
    @PostMapping(value = "/import",
            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE },
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> importFile(@RequestPart("file") MultipartFile csvFile) {
        log.info("Uploading new file.");
        if(CSVUtils.hasCSVFormat(csvFile)) {
            String msg = importMovieService.importMovies(csvFile);
            return ResponseEntity.status(HttpStatus.OK).body(msg);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new String("Not a CSV file"));

    }
}
