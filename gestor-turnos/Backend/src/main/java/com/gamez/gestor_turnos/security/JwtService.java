package com.gamez.gestor_turnos.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    // 🚨 EL SELLO DE AGUA (Firma Secreta) 🚨
    // Con esta clave secreta firmamos los tokens. Si alguien te roba esto, puede falsificar llaves.
    // (Es una frase muy larga codificada en Base64).
    private static final String SECRET_KEY = "VGhpcy1pcy1hLXZlcnktc2VjdXJlLWtleS1mb3ItZ2VzdG9yLXR1cm5vcy1wcm9qZWN0LW11c3QtYmUtbG9uZw==";

    // Método principal: Fabrica la pulsera VIP
    public String generarToken(String username, String rol) {
        
        // "Claims" son datos extra que metemos dentro de la pulsera
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("rol", rol); // Metemos si es ADMIN o USER para que React lo sepa luego

        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(username) // El "dueño" de la pulsera (usaremos el DNI o el nombre)
            .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de creación (AHORA)
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Caduca en 24 horas
            .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Lo firmamos con el sello de agua
            .compact(); // Lo comprime todo en el famoso texto JWT
    }

    // Traduce nuestra clave secreta de texto a un formato criptográfico que entiende Java
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Saca el DNI (Subject) de dentro del token
    public String extraerUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token) // Si el token está manipulado, esto da error
                .getBody()
                .getSubject();
    }

    // Saca el Rol (ADMIN o USER) de dentro del token
    public String extraerRol(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("rol", String.class);
    }

    // Comprueba que la firma matemática es correcta y no ha caducado
    public boolean isTokenValido(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false; // Si falla algo (falso, caducado...), devuelve false
        }
    }
}