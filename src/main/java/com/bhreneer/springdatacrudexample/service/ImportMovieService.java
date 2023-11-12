package com.bhreneer.springdatacrudexample.service;

import com.bhreneer.springdatacrudexample.model.dto.MovieCSVRecordDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ImportMovieService {

    String importMovies(MultipartFile file);

    void processCSVRecordFromQueue (MovieCSVRecordDTO recordDTO);
}
