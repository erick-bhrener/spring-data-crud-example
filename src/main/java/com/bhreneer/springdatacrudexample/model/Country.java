package com.bhreneer.springdatacrudexample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "COUNTRY", schema = "NETFLIX")
public class Country {

    @Id
    @GeneratedValue(generator = "COUNTRY_SEQ")
    @SequenceGenerator(name = "COUNTRY_SEQ", schema = "NETFLIX", sequenceName = "COUNTRY_ID_SEQ")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NAME_UPPER")
    private String nameUpper;
}
