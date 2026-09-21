package com.gamez.gestor_turnos.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamez.gestor_turnos.dto.AuthRequest;
import com.gamez.gestor_turnos.dto.AuthResponse;
import com.gamez.gestor_turnos.exception.ConflictoException;
import com.gamez.gestor_turnos.exception.RecursoNoEncontradoException;
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
        Empleado empleado = empleadoRepository.findByDni(peticion.getDni())
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        if (!passwordEncoder.matches(peticion.getPassword(), empleado.getPassword())) {
            throw new ConflictoException("Contraseña incorrecta");
        }

        String token = jwtService.generarToken(empleado);
        return new AuthResponse(token);
    }
}