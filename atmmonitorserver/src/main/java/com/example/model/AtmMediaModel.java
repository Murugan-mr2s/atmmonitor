package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "atmmedias")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class AtmMediaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    String bank;
    String atmid;
    String path;
    LocalDateTime observed_at;
    LocalDateTime created_at;

}
