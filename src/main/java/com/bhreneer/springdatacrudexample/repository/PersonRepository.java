package com.bhreneer.springdatacrudexample.repository;

import com.bhreneer.springdatacrudexample.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByNameUpper(String name);

    @Query("SELECT p FROM Person p " +
            "WHERE p.nameUpper like %:name% ")
    Page<Person> findAllPersonByFilter(@Param("name") String name, Pageable pageable);
}
