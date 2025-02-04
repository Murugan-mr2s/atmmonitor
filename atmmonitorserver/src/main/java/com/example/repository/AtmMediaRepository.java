package com.example.repository;

import com.example.model.AtmMediaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmMediaRepository extends JpaRepository<AtmMediaModel,Long> {
}
