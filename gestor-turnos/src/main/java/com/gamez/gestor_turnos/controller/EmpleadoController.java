package com.gamez.gestor_turnos.controller; 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamez.gestor_turnos.model.Empleado;
import com.gamez.gestor_turnos.repository.EmpleadoRepository;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    // 1. Conectamos al recepcionista con el archivero de la cocina
    @Autowired
    private EmpleadoRepository repositorio;

    // 2. Método GET: Cuando React pregunte quién trabaja, devolvemos la lista entera
    @GetMapping
    public List<Empleado> obtenerTodos() {
        return repositorio.findAll();
    }

    // 3. Método POST: Cuando React mande un empleado nuevo, lo guardamos
    @PostMapping
    public Empleado crearEmpleado(@RequestBody Empleado nuevoEmpleado) {
        return repositorio.save(nuevoEmpleado);
    }
}