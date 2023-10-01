package com.bhreneer.springdatacrudexample.repository;

import com.bhreneer.springdatacrudexample.model.Cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CastRepository extends JpaRepository<Cast, Long> {
}
