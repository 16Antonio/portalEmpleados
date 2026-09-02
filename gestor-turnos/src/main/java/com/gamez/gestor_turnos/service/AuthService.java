package com.gamez.gestor_turnos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamez.gestor_turnos.dto.AuthRequest;
import com.gamez.gestor_turnos.dto.AuthResponse;
import com.gamez.gestor_turnos.model.Empleado;
import com.gamez.gestor_turnos.repository.EmpleadoRepository;
import com.gamez.gestor_turnos.security.JwtService;

@Service
public class AuthService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private JwtService jwtService;

    public AuthResponse login(AuthRequest peticion) {
        // 1. Buscamos al empleado en la base de datos por su DNI
        Empleado empleado = empleadoRepository.findByDni(peticion.getDni())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Comprobamos si la contraseña es correcta 
        // (OJO: Aquí estamos comparando texto plano. En el futuro añadiremos encriptación BCrypt)
        if (!empleado.getPassword().equals(peticion.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // 3. Si todo es correcto, encendemos la fábrica y le damos su token
        String token = jwtService.generarToken(empleado.getDni(), empleado.getRol());
        
        return new AuthResponse(token);
    }
}