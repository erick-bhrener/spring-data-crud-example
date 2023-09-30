package com.bhreneer.springdatacrudexample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "MOVIE", schema = "NETFLIX")
public class Movie {

    @Id
    @GeneratedValue(generator = "MOVIE_SEQ")
    @SequenceGenerator(name = "MOVIE_SEQ", schema = "NETFLIX", sequenceName = "MOVIE_ID_SEQ")
    private Long id;

    @Column(name = "EXTERNAL_ID")
    private String externalId;

    @Column(name = "SHOW_ID")
    private String showId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "TITTLE")
    private String tittle;

    @Column(name = "SHOW_ID")
    private Country country;

    @Column(name = "DATE_ADDED")
    private String dateAdded;

    @Column(name = "RELEASE_YEAR")
    private String releaseYar;

    @Column(name = "RATING")
    private String rating;

    @Column(name = "DURATION")
    private String duration;

    @Column(name = "LISTED_IN")
    private String listedIn;

    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(mappedBy = "movie")
    private List<Cast> cast;
}
