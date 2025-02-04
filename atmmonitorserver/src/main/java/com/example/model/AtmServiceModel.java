package com.example.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "atmservices")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class AtmServiceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "atm_id")
    ATM atm;
    //private String bank;
    //private String atmid;
    private String userid;
    private AtmServiceType type;
    private AtmServiceStatus status;
    private LocalDateTime observed_at;
    private LocalDateTime created_at;
}
