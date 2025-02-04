package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "bankatms")
@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class ATM {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "bank", nullable = false)
    String bank;
    @Column(name = "atmid", nullable = false)
    String atmid;

    @OneToMany(mappedBy = "atm", cascade= CascadeType.ALL)
    List<AtmPingModel> atmpings ;

    @OneToMany(mappedBy = "atm",cascade= CascadeType.ALL)
    List<AtmServiceModel> atmservices;

    @OneToMany(mappedBy = "atm",cascade= CascadeType.ALL)
    List<AtmMediaModel> atmmedias ;

    @Column(name = "created_at", nullable = false)
    LocalDateTime created_at;
}
