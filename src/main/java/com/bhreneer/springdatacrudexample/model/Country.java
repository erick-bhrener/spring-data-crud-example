package com.bhreneer.springdatacrudexample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "COUNTRY", schema = "NETFLIX")
public class Country {

    @Id
    @GeneratedValue(generator = "COUNTRY_SEQ")
    @SequenceGenerator(name = "COUNTRY_SEQ", schema = "NETFLIX", sequenceName = "COUNTRY_ID_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NAME_UPPER")
    private String nameUpper;

    @ManyToMany(mappedBy = "countries", fetch = FetchType.LAZY)
    private List<Movie> movies;
}
