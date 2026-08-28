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

@Entity
@Table(name = "cuadrantes")
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

    public Long getIdCuadrante() {
        return idCuadrante;
    }

    public void setIdCuadrante(Long idCuadrante) {
        this.idCuadrante = idCuadrante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public TipoTurno getTipoTurno() {
        return tipoTurno;
    }

    public void setTipoTurno(TipoTurno tipoTurno) {
        this.tipoTurno = tipoTurno;
    }

    
}