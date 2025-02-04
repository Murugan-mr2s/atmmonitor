package com.example.repository;

import com.example.model.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ATMRepository extends JpaRepository<ATM,Long> {

    @Query(value = "SELECT * from bankatms where atmid=:atmid", nativeQuery = true)
    public Optional<ATM> findByAtmId(@Param("atmid") String atmid);
}
