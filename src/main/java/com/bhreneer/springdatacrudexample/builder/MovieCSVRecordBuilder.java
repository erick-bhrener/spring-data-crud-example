package com.bhreneer.springdatacrudexample.builder;

import com.bhreneer.springdatacrudexample.model.dto.MovieCSVRecordDTO;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class MovieCSVRecordBuilder {

    static String[] HEADERs = { "show_id","type","title","director","cast","country","date_added","release_year","rating","duration","listed_in","description" };

    public MovieCSVRecordDTO buildDTO (CSVRecord csvRecord) {
        return MovieCSVRecordDTO.builder()
                .cast(csvRecord.get("cast"))
                .type(csvRecord.get("type"))
                .country(csvRecord.get("country"))
                .description(csvRecord.get("description"))
                .director(csvRecord.get("director"))
                .title(csvRecord.get("title"))
                .duration(csvRecord.get("duration"))
                .showId(csvRecord.get("show_id"))
                .rating(csvRecord.get("rating"))
                .listedIn(csvRecord.get("listed_in"))
                .releaseYear(ObjectUtils.isEmpty(csvRecord.get("release_year")) ? null : Integer.valueOf(csvRecord.get("release_year")))
                .dataAdded(csvRecord.get("date_added"))
                .build();
    }
}
