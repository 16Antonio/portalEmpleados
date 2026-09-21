package com.gamez.gestor_turnos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamez.gestor_turnos.model.Cuadrante;
import com.gamez.gestor_turnos.service.CuadranteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cuadrantes")
@RequiredArgsConstructor


public class CuadranteController {



    private final CuadranteService cuadranteService;

    @GetMapping
    public List<Cuadrante> obtenerTodos() {
        return cuadranteService.obtenerTodos(); 
    }

    @PostMapping
    public Cuadrante asignarTurno(@RequestBody Cuadrante nuevoCuadrante) {
        return cuadranteService.asignarTurno(nuevoCuadrante); 
    }

    @PutMapping("/{id}")
    public Cuadrante actualizarCuadrante(@PathVariable Long id, @RequestBody Cuadrante cuadranteActualizado) {
        return cuadranteService.actualizarCuadrante(id, cuadranteActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminarCuadrante(@PathVariable Long id) {
        cuadranteService.eliminarCuadrante(id);
    }
}