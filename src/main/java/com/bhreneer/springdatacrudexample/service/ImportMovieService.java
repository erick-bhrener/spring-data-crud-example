package com.bhreneer.springdatacrudexample.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImportMovieService {

    void importMovies(MultipartFile file);
}
