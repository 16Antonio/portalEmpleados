package com.gamez.gestor_turnos.model;

import java.time.LocalDate;

import jakarta.persistence.Column; // Herramienta para guardar fechas (sin horas)
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cuadrantes")
@Getter
@Setter
@NoArgsConstructor
public class Cuadrante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuadrante;

    @Column(nullable = false)
    private LocalDate fecha; // Ej: 2026-08-30

    // ==========================================
    // RELACIONES (FOREIGN KEYS)
    // ==========================================

    // Varios cuadrantes pueden pertenecer a un mismo empleado
    @ManyToOne 
    @JoinColumn(name = "empleado_id", nullable = false) // Así se llamará la columna en MySQL
    private Empleado empleado;

    // Varios cuadrantes pueden usar el mismo tipo de turno (Ej: "Apertura")
    @ManyToOne
    @JoinColumn(name = "tipo_turno_id", nullable = false)
    private TipoTurno tipoTurno;

    
    
}