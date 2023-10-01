package com.bhreneer.springdatacrudexample.repository;

import com.bhreneer.springdatacrudexample.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByNameUpper(String name);
}
