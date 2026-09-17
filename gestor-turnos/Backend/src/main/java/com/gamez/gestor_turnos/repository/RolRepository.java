package com.gamez.gestor_turnos.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamez.gestor_turnos.model.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}