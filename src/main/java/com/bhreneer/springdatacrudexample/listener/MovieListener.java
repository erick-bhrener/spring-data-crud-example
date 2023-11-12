package com.bhreneer.springdatacrudexample.listener;

import com.bhreneer.springdatacrudexample.config.SpringDataCrudExampleConfig;
import com.bhreneer.springdatacrudexample.model.dto.MovieCSVRecordDTO;
import com.bhreneer.springdatacrudexample.service.ImportMovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MovieListener {

    private static final String MOVIE_QUEUE = "Movie.Queue";

    @Autowired
    private SpringDataCrudExampleConfig config;

    @Autowired
    private ImportMovieService importMovieService;

    @RabbitListener(queues = {MOVIE_QUEUE})
    public void processMovieListener (MovieCSVRecordDTO record) {
        log.info("MovieListener.processMovieListener - {}", record.toString());
        importMovieService.processCSVRecordFromQueue(record);
        log.info("MovieListener.processMovieListener - {} processed.", record.getShowId());
    }

}
