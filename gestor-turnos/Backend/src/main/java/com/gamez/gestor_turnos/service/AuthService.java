package com.gamez.gestor_turnos.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamez.gestor_turnos.dto.AuthRequest;
import com.gamez.gestor_turnos.dto.AuthResponse;
import com.gamez.gestor_turnos.model.Empleado;
import com.gamez.gestor_turnos.repository.EmpleadoRepository;
import com.gamez.gestor_turnos.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    
    private final EmpleadoRepository empleadoRepository;

    
    private final JwtService jwtService;

   
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest peticion) {
        // 1. Buscamos al empleado en la base de datos por su DNI
        Empleado empleado = empleadoRepository.findByDni(peticion.getDni())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Comprobamos si la contraseña es correcta 
        // (OJO: Aquí estamos comparando texto plano. En el futuro añadiremos encriptación BCrypt)
        if (!passwordEncoder.matches(peticion.getPassword(), empleado.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        // 3. Si todo es correcto, encendemos la fábrica y le damos su token
        String token = jwtService.generarToken(empleado);
        
        return new AuthResponse(token);
    }
}