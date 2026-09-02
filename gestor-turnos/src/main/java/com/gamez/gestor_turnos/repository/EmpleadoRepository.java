package com.gamez.gestor_turnos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamez.gestor_turnos.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    
    // Con esta simple línea, Spring Boot crea automáticamente la consulta SQL por debajo
    Optional<Empleado> findByDni(String dni);
    
}