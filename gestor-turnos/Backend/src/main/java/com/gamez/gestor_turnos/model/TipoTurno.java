package com.gamez.gestor_turnos.model;

import java.time.LocalTime;

import jakarta.persistence.Column; // Herramienta oficial de Java para manejar solo las horas
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipos_turno")
@Getter
@Setter
@NoArgsConstructor
public class TipoTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoTurno;

    @Column(nullable = false, unique = true)
    private String nombre; // Ej: "Apertura", "Cierre"

    @Column(nullable = false)
    private LocalTime horaInicio; // Usamos LocalTime en lugar de String

    @Column(nullable = false)
    private LocalTime horaFin;

    
}