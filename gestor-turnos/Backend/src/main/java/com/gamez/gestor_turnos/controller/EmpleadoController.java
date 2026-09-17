package com.gamez.gestor_turnos.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    // 1. Conectamos al recepcionista con el archivero de la cocina

    private final EmpleadoRepository repositorio;

    private final EmpleadoService empleadosService;

    // 2. Método GET: Cuando React pregunte quién trabaja, devolvemos la lista entera
    @GetMapping
    public List<EmpleadoResponseDTO> obtenerTodos() {
        return empleadosService.obtenerTodos();
    }

    // 3. Método POST: Cuando React mande un empleado nuevo, lo guardamos
    @PostMapping
    @PreAuthorize("hasAuthority('CREAR_EMPLEADO')")
    public Empleado crearEmpleado(@RequestBody Empleado nuevoEmpleado) {
        // ¡El controlador ya no encripta ni guarda! Simplemente le pasa el muerto al Servicio
        return empleadosService.crearEmpleado(nuevoEmpleado);
    }

     @PutMapping("/{id}")
    public Empleado actualizarEmpleado(@PathVariable Long id, @RequestBody Empleado datosNuevos) {
        // 1. Buscamos el empleado original en la caja fuerte
        Empleado empleadoOriginal = repositorio.findById(id)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        // 2. Le cambiamos los datos públicos
        empleadoOriginal.setDni(datosNuevos.getDni());
        empleadoOriginal.setNombre(datosNuevos.getNombre());
        empleadoOriginal.setApellidos(datosNuevos.getApellidos());
        empleadoOriginal.setPuesto(datosNuevos.getPuesto());
        empleadoOriginal.setDisponible(datosNuevos.isDisponible());
        empleadoOriginal.setObservaciones(datosNuevos.getObservaciones());
        
        // 3. Le asignamos el nuevo Rol
        empleadoOriginal.setRol(datosNuevos.getRol());
        // ¡FÍJATE QUE NO TOCAMOS EL PASSWORD! Así se queda el que tenía originalmente.
        // 4. Volvemos a guardar
        return repositorio.save(empleadoOriginal);
    }

    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) {
        repositorio.deleteById(id);
    }

    @PutMapping("/{id}/rol")
    public Empleado cambiarRol(@PathVariable Long id, @RequestBody Long idRol) {
        return empleadosService.cambiarRol(id, idRol);
    }
}
