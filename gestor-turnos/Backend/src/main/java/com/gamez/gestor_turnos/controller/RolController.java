package com.gamez.gestor_turnos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamez.gestor_turnos.model.Rol;
import com.gamez.gestor_turnos.service.RolService;

import lombok.RequiredArgsConstructor;



@RestController 
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor 


public class RolController {

    private final RolService rolService;

    @GetMapping
    public List<Rol> obtenerTodos() {
        return rolService.obtenerTodos();
    }
    
    @PostMapping
    public Rol crearRol(@RequestBody Rol nuevoRol){
        return rolService.crearRol(nuevoRol);
    }
    
    
}
