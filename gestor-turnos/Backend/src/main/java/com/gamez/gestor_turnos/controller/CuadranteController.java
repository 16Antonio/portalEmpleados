package com.gamez.gestor_turnos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@RestController
@RequestMapping("/api/v1/cuadrantes")
@CrossOrigin(origins = "http://localhost:5173")
public class CuadranteController {

    // ATENCIÓN: Aquí NO inyectamos el Repository, inyectamos tu Service
    @Autowired
    private CuadranteService cuadranteService;

    @GetMapping
    public List<Cuadrante> obtenerTodos() {
        return cuadranteService.obtenerTodos(); // Llama al método que creaste en el Service
    }

    @PostMapping
    public Cuadrante asignarTurno(@RequestBody Cuadrante nuevoCuadrante) {
        // Le pasamos la patata caliente a tu Service para que aplique la matemática
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