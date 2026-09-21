package com.gamez.gestor_turnos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamez.gestor_turnos.model.TipoTurno;


public interface TipoTurnoRepository extends JpaRepository<TipoTurno, Long> {
    
}