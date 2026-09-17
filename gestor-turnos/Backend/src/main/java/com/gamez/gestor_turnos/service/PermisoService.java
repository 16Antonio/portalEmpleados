package com.gamez.gestor_turnos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gamez.gestor_turnos.model.Permiso;
import com.gamez.gestor_turnos.repository.PermisoRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class PermisoService {
    
    private final   PermisoRepository permisoRepository;

    public List<Permiso> obtenerTodos(){

        return permisoRepository.findAll().stream()
            .collect(Collectors.toList());
        
    }

}
