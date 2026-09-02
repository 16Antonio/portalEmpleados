package com.gamez.gestor_turnos.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        
        // CHIVATO 1: Ver qué nos llega de Postman
        System.out.println("1. Cabecera recibida: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        
        // CHIVATO 2: Ver el token limpio
        System.out.println("2. Token limpio: " + token);

        if (jwtService.isTokenValido(token)) {
            // CHIVATO 3: Confirmar que la firma está bien
            System.out.println("3. ¡EL TOKEN ES VÁLIDO!");
            
            String dni = jwtService.extraerUsername(token);
            String rol = jwtService.extraerRol(token);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    dni, null, Collections.singletonList(new SimpleGrantedAuthority(rol))
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
             System.out.println("3. ❌ EL TOKEN HA DADO ERROR DE VALIDACIÓN");
        }

        filterChain.doFilter(request, response);
    }
}