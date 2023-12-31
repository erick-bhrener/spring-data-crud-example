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
@Table(name = "PERSON", schema = "NETFLIX")
public class Person {

    @Id
    @GeneratedValue(generator = "PERSON_SEQ")
    @SequenceGenerator(name = "PERSON_SEQ", schema = "NETFLIX", sequenceName = "PERSON_ID_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NAME_UPPER")
    private String nameUpper;

    @OneToMany(mappedBy = "person")
    private List<Cast> casts;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameUpper='" + nameUpper + '\'' +
                '}';
    }
}
