package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "atmpings")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class AtmPingModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "atm_id")
    ATM atm;
    //String bank;
    //String atmid;

    String status;
    LocalDateTime observed_at;
    LocalDateTime created_at;
}
