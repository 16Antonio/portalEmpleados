package com.gamez.gestor_turnos.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamez.gestor_turnos.dto.AuthRequest;
import com.gamez.gestor_turnos.dto.AuthResponse;
import com.gamez.gestor_turnos.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}