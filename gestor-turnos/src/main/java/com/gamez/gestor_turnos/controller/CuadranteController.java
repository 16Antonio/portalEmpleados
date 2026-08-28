package com.gamez.gestor_turnos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // Importamos el servicio
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamez.gestor_turnos.model.Cuadrante;
import com.gamez.gestor_turnos.service.CuadranteService;

@RestController
@RequestMapping("/api/v1/cuadrantes")
@CrossOrigin(origins = "http://localhost:5173")
public class CuadranteController {

    @Autowired
    private CuadranteService servicio; // CAMBIO CLAVE: Ahora llamamos al Servicio, no al Repositorio

    @GetMapping
    public List<Cuadrante> obtenerTodos() {
        return servicio.obtenerTodos();
    }

    @PostMapping
    public Cuadrante crearCuadrante(@RequestBody Cuadrante nuevoCuadrante) {
        // El controlador se lava las manos, el Servicio se encarga de pensar
        return servicio.asignarTurno(nuevoCuadrante); 
    }
}