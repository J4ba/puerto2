package com.CordyTech.cl.Puerto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Puerto")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Puerto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPuerto;

    @Column
    private String nombrePuerto;

    @Column(nullable=false)
    private float tarifaHora;
    
    @Column
    private float tarifaEslora;

    @Column(nullable=false)
    private boolean dispo; 

}
