package com.gamez.gestor_turnos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamez.gestor_turnos.model.TipoTurno;
import com.gamez.gestor_turnos.repository.TipoTurnoRepository;

@RestController
@RequestMapping("/api/v1/turnos") // ¡Nueva URL para los turnos!
@CrossOrigin(origins = "http://localhost:5173") 
public class TipoTurnoController {

    @Autowired
    private TipoTurnoRepository repositorio;

    @GetMapping
    public List<TipoTurno> obtenerTodos() {
        return repositorio.findAll();
    }

    @PostMapping
    public TipoTurno crearTurno(@RequestBody TipoTurno nuevoTurno) {
        return repositorio.save(nuevoTurno);
    }
}