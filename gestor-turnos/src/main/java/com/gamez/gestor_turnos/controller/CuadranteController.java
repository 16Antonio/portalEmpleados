package com.gamez.gestor_turnos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamez.gestor_turnos.model.Cuadrantes;
import com.gamez.gestor_turnos.repository.CuadrantesRepository;

@RestController
@RequestMapping("/api/v1/cuadrantes") // ¡Nueva URL para los cuadrantes!
@CrossOrigin(origins = "http://localhost:5173") 
public class CuadranteController {

    @Autowired
    private CuadrantesRepository repositorio;

    @GetMapping
    public List<Cuadrantes> obtenerTodos() {
        return repositorio.findAll();
    }

    @PostMapping
    public Cuadrantes crearCuadrante(@RequestBody Cuadrantes nuevoCuadrante) {
        return repositorio.save(nuevoCuadrante);
    }
}