package com.gamez.gestor_turnos.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gamez.gestor_turnos.model.Permiso;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    Optional<Permiso> findByNombre(String nombre);
}