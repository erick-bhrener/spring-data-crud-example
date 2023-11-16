package com.bhreneer.springdatacrudexample.repository;

import com.bhreneer.springdatacrudexample.model.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByName(String name);

    Optional<Country> findByNameUpper(String name);

    @Query("SELECT c FROM Country c " +
            "WHERE c.nameUpper like %:name%")
    Page<Country> findAllCountryByFilter(@Param("name") String name, Pageable pageable);

}
