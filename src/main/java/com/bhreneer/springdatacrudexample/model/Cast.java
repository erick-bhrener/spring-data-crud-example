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
@Table(name = "CAST", schema = "NETFLIX")
public class Cast {

    @Id
    @GeneratedValue(generator = "CAST_SEQ")
    @SequenceGenerator(name = "CAST_SEQ", schema = "NETFLIX", sequenceName = "CAST_ID_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person person;

    @Column(name = "ROLE")
    private String role;

}
