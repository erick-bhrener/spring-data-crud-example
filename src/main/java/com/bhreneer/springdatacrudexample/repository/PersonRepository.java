package com.bhreneer.springdatacrudexample.repository;

import com.bhreneer.springdatacrudexample.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
