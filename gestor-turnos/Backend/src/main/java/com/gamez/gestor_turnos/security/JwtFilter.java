package com.gamez.gestor_turnos.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");


        System.out.println("1. Cabecera recibida: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);


        System.out.println("2. Token limpio: " + token);

        if (jwtService.isTokenValido(token)) {
            System.out.println("3. ¡EL TOKEN ES VÁLIDO!");

            String dni = jwtService.extraerUsername(token);
            // Sacamos la lista de permisos del token
            List<String> permisos = jwtService.extraerPermisos(token);


            List<SimpleGrantedAuthority> authorities = permisos.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    dni, null, authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
            System.out.println("3. ❌ EL TOKEN HA DADO ERROR DE VALIDACIÓN");
        }

        filterChain.doFilter(request, response);
    }
}
