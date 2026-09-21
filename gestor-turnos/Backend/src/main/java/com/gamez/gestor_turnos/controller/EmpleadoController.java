package com.gamez.gestor_turnos.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamez.gestor_turnos.dto.EmpleadoResponseDTO;
import com.gamez.gestor_turnos.model.Empleado;
import com.gamez.gestor_turnos.repository.EmpleadoRepository;
import com.gamez.gestor_turnos.service.EmpleadoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController

@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
public class EmpleadoController {


    private final EmpleadoRepository repositorio;

    private final EmpleadoService empleadosService;


    @GetMapping
    @PreAuthorize("hasAuthority('VER_EMPLEADOS')")
    public List<EmpleadoResponseDTO> obtenerTodos() {
        return empleadosService.obtenerTodos();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREAR_EMPLEADO')")
    public Empleado crearEmpleado(@Valid @RequestBody Empleado nuevoEmpleado) {
    
        return empleadosService.crearEmpleado(nuevoEmpleado);
    }

    @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado datosNuevos) {
        Empleado empleadoOriginal = repositorio.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        empleadoOriginal.setDni(datosNuevos.getDni());
        empleadoOriginal.setNombre(datosNuevos.getNombre());
        empleadoOriginal.setApellidos(datosNuevos.getApellidos());
        empleadoOriginal.setPuesto(datosNuevos.getPuesto());
        empleadoOriginal.setDisponible(datosNuevos.isDisponible());
        empleadoOriginal.setObservaciones(datosNuevos.getObservaciones());
        
        
        empleadoOriginal.setRol(datosNuevos.getRol());
        return repositorio.save(empleadoOriginal);
    }

    @DeleteMapping("/{id}")
     @PreAuthorize("hasAuthority('BORRAR_EMPLEADO')")
    public void eliminarEmpleado(@PathVariable Long id) {
        repositorio.deleteById(id);
    }

    @PutMapping("/{id}/rol")
    public Empleado cambiarRol(@PathVariable Long id, @RequestBody Long idRol) {
        return empleadosService.cambiarRol(id, idRol);
    }
}
