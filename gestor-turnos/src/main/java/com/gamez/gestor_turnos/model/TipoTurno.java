package com.gamez.gestor_turnos.model;

import java.time.LocalTime;

import jakarta.persistence.Column; // Herramienta oficial de Java para manejar solo las horas
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipos_turno")
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

    public Long getIdTipoTurno() {
        return idTipoTurno;
    }

    public void setIdTipoTurno(Long idTipoTurno) {
        this.idTipoTurno = idTipoTurno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    
}