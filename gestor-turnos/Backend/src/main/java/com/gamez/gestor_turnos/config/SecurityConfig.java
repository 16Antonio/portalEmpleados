package com.gamez.gestor_turnos.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.gamez.gestor_turnos.security.JwtFilter;

@Configuration
@EnableWebSecurity // Enciende la seguridad web
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // 1. Solucionamos el problema de CORS (para que React no se bloquee antes de tiempo)
            .cors(Customizer.withDefaults()) 
            
            // 2. Desactivamos CSRF (una protección para webs tradicionales, no para APIs con React)
            .csrf(csrf -> csrf.disable()) 
            
            // 3. Le decimos que no guarde sesiones en memoria (vamos a usar Tokens temporales)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 
            
            // 4. AQUI DEFINIMOS QUÉ ESTÁ ABIERTO Y QUÉ ESTÁ CERRADO
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll() // Dejamos pública esta ruta para que puedan hacer Login
                .anyRequest().authenticated()
                
            )

            .addFilterBefore(jwtFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    // Configuración detallada para permitir que React (puerto 5173) hable con Java
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}