package com.gamez.gestor_turnos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamez.gestor_turnos.model.Permiso;
import com.gamez.gestor_turnos.service.PermisoService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/permisos")
@RequiredArgsConstructor

@CrossOrigin(origins="http://localhost:5173")
public class PermisoController {

    private final PermisoService permisoService;

    @GetMapping
    public List<Permiso> obtenerTodos() {
        return permisoService.obtenerTodos();
    }
    
    
}
