package com.gamez.gestor_turnos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamez.gestor_turnos.dto.EmpleadoResponseDTO;
import com.gamez.gestor_turnos.model.Empleado;
import com.gamez.gestor_turnos.model.Rol;
import com.gamez.gestor_turnos.repository.EmpleadoRepository;
import com.gamez.gestor_turnos.repository.RolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class EmpleadoService {
    
    
    private final  EmpleadoRepository empleadoRepository;

    private final RolRepository rolRepository;

    private final  PasswordEncoder passwordEncoder;

    public List<EmpleadoResponseDTO> obtenerTodos() {
        return empleadoRepository.findAll().stream()
            .map(empleado -> new EmpleadoResponseDTO(empleado))
            .collect(Collectors.toList());
    }

    public Empleado crearEmpleado( Empleado nuevoEmpleado) {
        nuevoEmpleado.setPassword(passwordEncoder.encode(nuevoEmpleado.getPassword()));

        return empleadoRepository.save(nuevoEmpleado);
    }

    public Empleado cambiarRol(Long idEmpleado, Long idRol) {
        // Buscamos al empleado
        Empleado empleado = empleadoRepository.findById(idEmpleado)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        // Buscamos el rol
        Rol nuevoRol = rolRepository.findById(idRol)
            .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        // Se lo asignamos y guardamos
        empleado.setRol(nuevoRol);
        return empleadoRepository.save(empleado);
    }
}
