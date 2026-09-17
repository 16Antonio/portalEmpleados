package com.gamez.gestor_turnos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gamez.gestor_turnos.model.Rol;
import com.gamez.gestor_turnos.repository.RolRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class RolService {
    
    private final RolRepository rolRepository;

    public List<Rol> obtenerTodos(){
        return rolRepository.findAll().stream()
        .collect(Collectors.toList());
    }

    public Rol crearRol(Rol nuevoRol) {
        return rolRepository.save(nuevoRol);
    }

}
