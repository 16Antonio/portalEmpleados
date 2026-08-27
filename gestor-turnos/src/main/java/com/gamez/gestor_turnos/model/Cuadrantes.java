package com.gamez.gestor_turnos.model;

import java.time.LocalTime;

import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "cuadrnates")
public class Cuadrantes{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long idCuadrante;

    @Column(nullable = false)
    private String id_empleado;

    @Column (nullable = false)
    private String id_tipo_turno;

    @Column (nullable = false)
    private LocalTime fecha;

    @Column (nullable = false)
    private String puesto;

    public long getIdCuadrante() {
        return idCuadrante;
    }

    public void setIdCuadrante(long idCuadrante) {
        this.idCuadrante = idCuadrante;
    }

    public String getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(String id_empleado) {
        this.id_empleado = id_empleado;
    }

    public String getId_tipo_turno() {
        return id_tipo_turno;
    }

    public void setId_tipo_turno(String id_tipo_turno) {
        this.id_tipo_turno = id_tipo_turno;
    }

    public LocalTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalTime fecha) {
        this.fecha = fecha;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }


}