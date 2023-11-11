package com.bhreneer.springdatacrudexample.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImportMovieService {

    String importMovies(MultipartFile file);
}
