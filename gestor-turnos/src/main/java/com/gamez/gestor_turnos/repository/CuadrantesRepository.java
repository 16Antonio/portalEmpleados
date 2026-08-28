package com.gamez.gestor_turnos.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamez.gestor_turnos.model.Cuadrante;
import com.gamez.gestor_turnos.model.Empleado;

@Repository
public interface CuadrantesRepository extends JpaRepository<Cuadrante, Long> {
    

    public List<Cuadrante> findByFechaAndEmpleado( LocalDate fecha, Empleado empleado);

    public List<Cuadrante> findByEmpleadoAndFecha(Empleado empleado, LocalDate fecha);

}
