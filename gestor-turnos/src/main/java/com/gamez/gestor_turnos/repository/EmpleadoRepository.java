package com.gamez.gestor_turnos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamez.gestor_turnos.model.Empleado;

@Repository // Le dice a Spring: "Este es el archivero de la base de datos"
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    
    // Al extender de JpaRepository, Java ya sabe hacer INSERT, SELECT, UPDATE y DELETE.
}