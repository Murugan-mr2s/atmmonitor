package com.example.repository;

import com.example.model.AtmPingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmPingRepository extends JpaRepository<AtmPingModel,Long> {
}
