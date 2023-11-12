package com.bhreneer.springdatacrudexample.publisher;

import com.bhreneer.springdatacrudexample.config.SpringDataCrudExampleConfig;
import com.bhreneer.springdatacrudexample.model.dto.MovieCSVRecordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MoviePublisher {

    @Autowired
    private SpringDataCrudExampleConfig config;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void processMovieCSVRecord(MovieCSVRecordDTO recordDTO) {
        log.info("MoviePublisher.processMovieCSVRecord - {}", recordDTO.toString());
        rabbitTemplate.convertAndSend(config.getExchange(), config.getRoutingkey(), recordDTO);
        log.info("MoviePublisher.processMovieCSVRecord - sent");
    }
}
