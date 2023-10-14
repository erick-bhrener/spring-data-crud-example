package com.bhreneer.springdatacrudexample.repository;

import com.bhreneer.springdatacrudexample.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByName(String name);

    Optional<Country> findByNameUpper(String name);

}
