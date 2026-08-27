package com.gamez.gestor_turnos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamez.gestor_turnos.model.Cuadrantes;

@Repository
public interface CuadrantesRepository extends JpaRepository<Cuadrantes, Long> {
    
}
